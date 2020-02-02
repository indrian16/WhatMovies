package io.indrian.moviecatalogue.ui.tvshowdetail

import android.annotation.SuppressLint
import android.content.Intent
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
import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.adapter.GenreChipAdapter
import io.indrian.moviecatalogue.adapter.ViewPagerDetailAdapter
import io.indrian.moviecatalogue.data.model.Genre
import io.indrian.moviecatalogue.data.model.TVShow
import io.indrian.moviecatalogue.ui.settings.SettingsActivity
import io.indrian.moviecatalogue.ui.tvshowcast.TVShowCastFragment
import io.indrian.moviecatalogue.ui.tvshowinfo.TVShowInfoFragment
import io.indrian.moviecatalogue.utils.showToast
import io.indrian.moviecatalogue.utils.toVisible
import kotlinx.android.synthetic.main.activity_tvshow_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

class TVShowDetailActivity : AppCompatActivity(), GenreChipAdapter.OnGenreCallBack {

    companion object {

        const val EXTRA_TV_SHOW = "extra_tv_show"
    }

    private val mAdapter = GenreChipAdapter(this)

    private val tvShowDetailVM: TVShowDetailVM by viewModel { parametersOf(Bundle()) }
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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_detail)

        intent.getParcelableExtra<TVShow>(EXTRA_TV_SHOW)?.let { tvShow ->

            Glide.with(this)
                .load(tvShow.backdrop)
                .into(img_backdrop)

            Glide.with(this)
                .load(tvShow.poster)
                .into(img_poster)

            tv_name.text = tvShow.name
            tv_year.text = tvShow.year
            tv_vote_average.text = tvShow.voteAverage.toString()
            rb_vote_average.rating = (tvShow.voteAverage / 2).toFloat()
            tv_vote_count.text = "${tvShow.voteCount} ${getString(R.string.voters)}"

            setToolbar(tvShow.name)
            setViewPager(tvShow.id)

            if (savedInstanceState == null) tvShowDetailVM.getDetailTVShow(tvShow.id)
        }
        setViewModel()
        setRv()
    }

    private fun setRv() {

        rv_genre.adapter = mAdapter
    }

    private fun setViewModel() {

        tvShowDetailVM.genreTVShowState.observe(this, genreTVShowStateObserver)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.action_setting -> {

                startActivity(Intent(baseContext, SettingsActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {

        finish()
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            finish()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {

        tvShowDetailVM.genreTVShowState.removeObserver(genreTVShowStateObserver)
        super.onDestroy()
    }

    private fun startShimmer() {

        shimmer_genre.startShimmer()
        shimmer_genre.toVisible()

        rv_genre.toVisible(false)
    }

    private fun stopShimmer() {

        shimmer_genre.stopShimmer()
        shimmer_genre.toVisible(false)
    }

    private fun genreIsLoaded(genres: List<Genre> = arrayListOf()) {

        if (genres.isNotEmpty()) {

            rv_genre.toVisible()
            mAdapter.addNewItem(genres)
        }
    }

    override fun onClickItem(genre: Genre) {

        showToast("Click: "+genre.name+" | ${getString(R.string.coming_soon)}")
    }
}
