package io.indrian.whatmovies.ui.tvshowdetail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.github.ajalt.timberkt.d
import com.google.android.material.appbar.AppBarLayout
import io.indrian.whatmovies.R
import io.indrian.whatmovies.adapter.GenreChipAdapter
import io.indrian.whatmovies.adapter.ViewPagerDetailAdapter
import io.indrian.whatmovies.data.model.Genre
import io.indrian.whatmovies.data.model.TVShow
import io.indrian.whatmovies.ui.settings.SettingsActivity
import io.indrian.whatmovies.ui.tvshowcast.TVShowCastFragment
import io.indrian.whatmovies.ui.tvshowinfo.TVShowInfoFragment
import io.indrian.whatmovies.utils.showToast
import io.indrian.whatmovies.utils.setVisibility
import kotlinx.android.synthetic.main.activity_tvshow_detail.*
import kotlinx.android.synthetic.main.genre_shimmer.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TVShowDetailActivity : AppCompatActivity(), GenreChipAdapter.OnGenreCallBack {

    companion object {

        const val EXTRA_TV_SHOW = "extra_tv_show"
        const val RESULT_CODE = 110
    }

    private val tvShowDetailVM: TVShowDetailVM by viewModel { parametersOf(Bundle()) }
    private val mAdapter = GenreChipAdapter(this)
    private lateinit var currentTVShow: TVShow
    private lateinit var favoriteIcon: Drawable

    private val genreTVShowStateObserver = Observer<GenreTVShowState> { state ->

        when (state) {

            is GenreTVShowState.Loading -> {

                d { "GenreState: Loading" }
                startShimmer()
            }

            is GenreTVShowState.Loaded -> {

                d { "GenreState: Loaded" }
                stopShimmer()
                genreIsLoaded(state.genres)
            }

            is GenreTVShowState.Error -> {

                d { "GenreState: Error" }
                stopShimmer()
                showToast("error")
            }
        }
    }

    private val favoriteTVShowStateObserver = Observer<FavoriteTVShowState> { state ->

        when (state) {

            is FavoriteTVShowState.Exist -> {

                if (state.action) {

                    showToast(resources.getString(R.string.add_favorite))
                } else {

                    d { "FavoriteTVShowState.Exist" }
                }

                favoriteIcon = ContextCompat.getDrawable(baseContext, R.drawable.ic_heart)!!
            }
            is FavoriteTVShowState.NotExist -> {

                if (state.action) {

                    showToast(resources.getString(R.string.del_favorite))
                } else {

                    d { "FavoriteTVShowState.NotExist" }
                }

                favoriteIcon = ContextCompat.getDrawable(baseContext, R.drawable.ic_heart_empty)!!
            }
            is FavoriteTVShowState.Error -> {

                d { "FavoriteTVShowState.Error" }
                showToast(resources.getString(R.string.occured_error))
                favoriteIcon = ContextCompat.getDrawable(baseContext, R.drawable.ic_heart_empty)!!
            }
        }

        invalidateOptionsMenu()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_detail)

        setRv()
        setViewModel()

        intent.getParcelableExtra<TVShow>(EXTRA_TV_SHOW)?.let { tvShow ->

            currentTVShow = tvShow

            Glide.with(this)
                .load(tvShow.backdrop)
                .into(img_backdrop)

            Glide.with(this)
                .load(tvShow.poster)
                .into(img_poster)

            tv_name.text = tvShow.name
            tv_year.text = tvShow.year
            tv_vote_average.text = tvShow.voteAverage.toString()
            rb_vote_average.rating = tvShow.fiveStart
            tv_vote_count.text = "${tvShow.voteCount} ${getString(R.string.voters)}"

            setToolbar(tvShow.name)
            setViewPager(tvShow.id)

            if (savedInstanceState == null) {

                tvShowDetailVM.getDetailTVShow(tvShow.id)
                tvShowDetailVM.getTVShowIsExist(tvShow.id)
            }
        }
    }

    private fun setRv() {

        rv_genre.adapter = mAdapter
    }

    private fun setViewModel() {

        tvShowDetailVM.genreTVShowState.observe(this, genreTVShowStateObserver)
        tvShowDetailVM.favoriteTVShowState.observe(this, favoriteTVShowStateObserver)
    }

    private fun setToolbar(name: String) {

        toolbar_detail.overflowIcon?.setTint(ContextCompat.getColor(baseContext, R.color.colorPrimary))
        setSupportActionBar(toolbar_detail)
        supportActionBar?.let {

            it.title = name
            it.setDisplayHomeAsUpEnabled(true)
        }
        app_bar_detail.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {

            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {

                if (scrollRange == -1) {

                    scrollRange = appBarLayout!!.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {

                    collapsing_detail.title = name
                    isShow = true

                } else if(isShow) {

                    collapsing_detail.title = " "
                    isShow = false
                }
            }
        })
    }

    private fun setViewPager(id: Int) {

        val pages = arrayListOf(
            TVShowInfoFragment.newInstance(id),
            TVShowCastFragment()
        )
        val mAdapter = ViewPagerDetailAdapter(baseContext, supportFragmentManager)
        mAdapter.addPages(pages)
        view_pager_detail.adapter = mAdapter
        tab_layout_detail.setupWithViewPager(view_pager_detail)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        menu?.findItem(R.id.action_favorite)?.icon = favoriteIcon
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.action_setting -> {

                startActivity(Intent(baseContext, SettingsActivity::class.java))
            }

            R.id.action_favorite -> {

                if (tvShowDetailVM.favoriteTVShowState.value is FavoriteTVShowState.NotExist) {

                    tvShowDetailVM.addFavorite(currentTVShow)
                } else {

                    tvShowDetailVM.delFavorite(currentTVShow)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {

        setResult(RESULT_CODE)
        finish()
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            onSupportNavigateUp()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {

        tvShowDetailVM.genreTVShowState.removeObserver(genreTVShowStateObserver)
        tvShowDetailVM.favoriteTVShowState.removeObserver(favoriteTVShowStateObserver)
        super.onDestroy()
    }

    private fun startShimmer() {

        shimmer_genre.startShimmer()
        shimmer_genre.setVisibility()

        rv_genre.setVisibility(false)
    }

    private fun stopShimmer() {

        shimmer_genre.stopShimmer()
        shimmer_genre.setVisibility(false)
    }

    private fun genreIsLoaded(genres: List<Genre> = arrayListOf()) {

        if (genres.isNotEmpty()) {

            rv_genre.setVisibility()
            mAdapter.addNewItem(genres)
        }
    }

    override fun onClickItem(genre: Genre) {

        showToast("Click: "+genre.name+" | ${getString(R.string.coming_soon)}")
    }
}
