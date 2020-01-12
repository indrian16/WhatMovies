package io.indrian.moviecatalogue.ui.moviedetail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
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
import io.indrian.moviecatalogue.utils.showToast
import io.indrian.moviecatalogue.utils.toVisible
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.koin.android.ext.android.inject
import java.util.*
import kotlin.collections.ArrayList

class MovieDetailActivity : AppCompatActivity(), GenreChipAdapter.OnGenreCallBack {

    private val viewModel: MovieDetailVM by inject()
    private val mAdapter = GenreChipAdapter(this)

    companion object {

        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_GENRES = "extra_genres"
    }

    private var genreArray = ArrayList<Genre>()
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
                genreArray = ArrayList(state.genres)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        intent.getParcelableExtra<Movie>(EXTRA_MOVIE)?.let { movie ->

            Glide.with(this)
                .load(movie.poster)
                .into(img_poster)
            Glide.with(this)
                .load(movie.backdrop)
                .into(img_backdrop)

            tv_year.text = movie.releaseDate[Calendar.YEAR].toString()
            tv_title.text = movie.title
            tv_vote_average.text = movie.voteAverage.toString()
            rb_vote_average.rating = (movie.voteAverage / 2).toFloat()
            tv_vote_count.text = "${movie.voteCount} ${getString(R.string.voters)}"

            setToolbar(movie.title)
            setViewPager(movie.id)

            if (savedInstanceState == null) {

                setViewModel(movie.id)
            }
        }

        setRv()
    }

    private fun setRv() {

        rv_genre.adapter = mAdapter
    }

    private fun setViewModel(id: Int) {

        viewModel.getMovieGenres(id)
        viewModel.movieGenreState.observe(this, movieGenreStateObserver)
    }

    private fun setToolbar(name: String) {

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

        viewModel.movieGenreState.removeObserver(movieGenreStateObserver)
        super.onDestroy()
    }

    private fun startShimmer() {

        shimmer_genre.startShimmer()
        shimmer_genre.toVisible()
    }

    private fun stopShimmer() {

        shimmer_genre.startShimmer()
        shimmer_genre.toVisible(false)
    }

    private fun genreMovieIsLoaded(genres: List<Genre> = arrayListOf()) {

        if (genres.isNotEmpty()) {

            rv_genre.toVisible()
            mAdapter.addNewItem(genres)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {

        outState.putParcelableArrayList(EXTRA_GENRES, genreArray)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        stopShimmer()
        val restoreGenre = savedInstanceState.getParcelableArrayList<Genre>(EXTRA_GENRES)
        genreArray = restoreGenre!!
        genreMovieIsLoaded(restoreGenre.toList())
    }

    override fun onClickItem(genre: Genre) {

        showToast("You click ${genre.name} | ${getString(R.string.coming_soon)}")
    }
}
