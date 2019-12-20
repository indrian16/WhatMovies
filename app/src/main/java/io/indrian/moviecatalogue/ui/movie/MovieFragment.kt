package io.indrian.moviecatalogue.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ajalt.timberkt.Timber

import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.data.model.Movie
import io.indrian.moviecatalogue.adapter.MovieAdapter
import io.indrian.moviecatalogue.ui.moviedetail.DetailMovieActivity
import io.indrian.moviecatalogue.utils.showToast
import io.indrian.moviecatalogue.utils.visible
import kotlinx.android.synthetic.main.fragment_movie.*
import org.koin.android.ext.android.inject

class MovieFragment : Fragment(), MovieAdapter.OnMovieClickCallback {

    companion object {

        fun newInstance() = MovieFragment()
    }

    private val movieVM: MovieVM by inject()
    private val mAdapter = MovieAdapter(this)

    private val movieListStateObserver = Observer<MoviesListState> { state ->

        when (state) {

            is MoviesListState.Loading -> {

                Timber.d { "MovieLoading" }
                startShimmer()
                isMoviesLoaded()
            }

            is MoviesListState.Error -> {

                Timber.d { "MovieError" }
                showToast("Error: ${state.message}")
                stopShimmer()
                isMoviesLoaded()
            }

            is MoviesListState.Loaded -> {

                Timber.d { "MovieLoaded" }
                stopShimmer()
                isMoviesLoaded(state.movies)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupVM()
    }

    private fun setupVM() {

        movieVM.getMovies()
        movieVM.movieListState.observe(this, movieListStateObserver)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.movie_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_movie_refresh) {

            movieVM.getMovies()
            rv_movie.smoothScrollToPosition( 0)
            return true
        }

        return false
    }

    private fun setupView() {

        rv_movie.apply {

            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }
    }

    override fun onMovieClickItem(movie: Movie, imgPoster: ImageView) {

        val intent = Intent(activity, DetailMovieActivity::class.java)
        intent.run {

            this.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie)
            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, imgPoster, getString(R.string.tn_poster))
            startActivity(intent, option.toBundle())
        }
    }

    override fun onDetach() {

        movieVM.movieListState.removeObserver(movieListStateObserver)
        super.onDetach()
    }

    private fun startShimmer() {

        shimmer_movie_container.startShimmer()
        shimmer_movie_container.visible(indicator = true)
    }

    private fun stopShimmer() {

        shimmer_movie_container.stopShimmer()
        shimmer_movie_container.visible(indicator = false)
    }

    private fun isMoviesLoaded(movies: List<Movie> = arrayListOf()) {

        if (movies.isNotEmpty()) {

            rv_movie.visible(indicator = true)
            mAdapter.update(movies)

        } else {

            rv_movie.visible(indicator = false)
        }
    }
}
