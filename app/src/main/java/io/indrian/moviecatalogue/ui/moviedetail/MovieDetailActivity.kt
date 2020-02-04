package io.indrian.moviecatalogue.ui.moviedetail

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
import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.adapter.GenreChipAdapter
import io.indrian.moviecatalogue.adapter.ViewPagerDetailAdapter
import io.indrian.moviecatalogue.data.model.Genre
import io.indrian.moviecatalogue.data.model.Movie
import io.indrian.moviecatalogue.ui.moviecast.MovieCastFragment
import io.indrian.moviecatalogue.ui.movieinfo.MovieInfoFragment
import io.indrian.moviecatalogue.ui.settings.SettingsActivity
import io.indrian.moviecatalogue.utils.showToast
import io.indrian.moviecatalogue.utils.visibility
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieDetailActivity : AppCompatActivity(), GenreChipAdapter.OnGenreCallBack {

    private val movieDetailVM: MovieDetailVM by viewModel { parametersOf(Bundle()) }
    private val mAdapter = GenreChipAdapter(this)
    private lateinit var currentMovie: Movie
    private lateinit var favoriteIcon: Drawable

    companion object {

        const val EXTRA_MOVIE = "extra_movie"
        const val RESULT_CODE = 110
    }

    private val movieGenreStateObserver = Observer<MovieGenreState> { state ->

        when (state) {

            is MovieGenreState.Loading -> {

                d { "MovieGenreState.Loading" }
                startShimmer()
            }
            is MovieGenreState.Error -> {

                d { "MovieGenreState.Error" }
                stopShimmer()
            }
            is MovieGenreState.Loaded -> {

                d { "MovieGenreState.Loaded" }
                stopShimmer()
                genreMovieIsLoaded(state.genres)
            }
        }
    }

    private val favoriteMovieStateObserver = Observer<FavoriteMovieState> { state ->

        when (state) {

            is FavoriteMovieState.Exist -> {

                if (state.action) {

                    showToast(resources.getString(R.string.add_favorite))
                } else {

                    d { "FavoriteMovieState.Exist" }
                }

                favoriteIcon = ContextCompat.getDrawable(baseContext, R.drawable.ic_heart)!!
            }
            is FavoriteMovieState.NotExist -> {

                if (state.action) {

                    showToast(resources.getString(R.string.del_favorite))
                } else {

                    d { "FavoriteMovieState.NotExist" }
                }

                favoriteIcon = ContextCompat.getDrawable(baseContext, R.drawable.ic_heart_empty)!!
            }
            is FavoriteMovieState.Error -> {

                d { "FavoriteMovieState.Error" }
                showToast(resources.getString(R.string.occured_error))
                favoriteIcon = ContextCompat.getDrawable(baseContext, R.drawable.ic_heart_empty)!!
            }
        }

        invalidateOptionsMenu()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        intent.getParcelableExtra<Movie>(EXTRA_MOVIE)?.let { movie ->

            currentMovie = movie

            Glide.with(this)
                .load(movie.poster)
                .into(img_poster)
            Glide.with(this)
                .load(movie.backdrop)
                .into(img_backdrop)

            tv_year.text = movie.year
            tv_title.text = movie.title
            tv_vote_average.text = movie.voteAverage.toString()
            rb_vote_average.rating = (movie.voteAverage / 2).toFloat()
            tv_vote_count.text = "${movie.voteCount} ${getString(R.string.voters)}"

            setToolbar(movie.title)
            setViewPager(movie.id)

            if (savedInstanceState == null) {

                movieDetailVM.getMovieGenres(movie.id)
                movieDetailVM.getMoviesIsExist(movie.id)
            }
        }

        setViewModel()
        setRv()
    }

    private fun setRv() {

        rv_genre.adapter = mAdapter
    }

    private fun setViewModel() {

        movieDetailVM.movieGenreState.observe(this, movieGenreStateObserver)
        movieDetailVM.favoriteMovieState.observe(this, favoriteMovieStateObserver)
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
            MovieInfoFragment.newInstance(id),
            MovieCastFragment()
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

                if (movieDetailVM.favoriteMovieState.value is FavoriteMovieState.NotExist) {

                    movieDetailVM.addFavorite(currentMovie)
                } else {

                    movieDetailVM.delFavorite(currentMovie)
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

        movieDetailVM.movieGenreState.removeObserver(movieGenreStateObserver)
        movieDetailVM.favoriteMovieState.removeObserver(favoriteMovieStateObserver)
        super.onDestroy()
    }

    private fun startShimmer() {

        shimmer_genre.startShimmer()
        shimmer_genre.visibility()
    }

    private fun stopShimmer() {

        shimmer_genre.startShimmer()
        shimmer_genre.visibility(false)
    }

    private fun genreMovieIsLoaded(genres: List<Genre> = arrayListOf()) {

        if (genres.isNotEmpty()) {

            rv_genre.visibility()
            mAdapter.addNewItem(genres)
        }
    }

    override fun onClickItem(genre: Genre) {

        showToast("You click ${genre.name} | ${getString(R.string.coming_soon)}")
    }
}
