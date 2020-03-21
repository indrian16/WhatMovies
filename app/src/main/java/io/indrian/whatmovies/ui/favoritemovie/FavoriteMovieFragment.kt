package io.indrian.whatmovies.ui.favoritemovie

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import com.github.ajalt.timberkt.Timber.d

import io.indrian.whatmovies.R
import io.indrian.whatmovies.adapter.MovieAdapter
import io.indrian.whatmovies.data.model.Movie
import io.indrian.whatmovies.ui.moviedetail.MovieDetailActivity
import io.indrian.whatmovies.utils.showToast
import io.indrian.whatmovies.utils.setVisibility
import kotlinx.android.synthetic.main.fragment_favorite_movie.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FavoriteMovieFragment : Fragment(), MovieAdapter.OnMovieClickCallback {

    companion object {

        private const val REQUEST_CODE = 100
    }

    private val favoriteMovieVM: FavoriteMovieVM by viewModel { parametersOf(Bundle()) }
    private val mAdapter =  MovieAdapter(this)

    private val movieListStateObserver = Observer<FavoriteMovieListState> { state ->

        when (state) {

            is FavoriteMovieListState.Loading -> {

                d { "FavoriteMovieListState.Loading" }
                startShimmer()
                isMoviesLoaded()
            }

            is FavoriteMovieListState.Error -> {

                d { "FavoriteMovieListState.Error" }
                showToast("Error: ${state.message}")
                stopShimmer()
                isMoviesLoaded()
            }

            is FavoriteMovieListState.Loaded -> {

                d { "FavoriteMovieListState.Loaded" }
                stopShimmer()
                isMoviesLoaded(state.movies)
            }

            is FavoriteMovieListState.Empty -> {

                d { "FavoriteMovieListState.Empty" }
                stopShimmer()
                empty_movie_state.setVisibility(true)
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
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRv()
        setupViewModel()

        if (savedInstanceState == null) favoriteMovieVM.getFavoriteMovies()
    }

    private fun setupRv() {

        rv_favorite_movie.adapter = mAdapter
    }

    private fun setupViewModel() {

        favoriteMovieVM.favoriteMovieListState.observe(activity!!, movieListStateObserver)
    }

    private fun startShimmer() {

        shimmer_favorite_movie_container.startShimmer()
        shimmer_favorite_movie_container.setVisibility()

        rv_favorite_movie.setVisibility(visible = false)
        empty_movie_state.setVisibility(visible = false)
    }

    private fun stopShimmer() {

        shimmer_favorite_movie_container.stopShimmer()
        shimmer_favorite_movie_container.setVisibility(visible = false)
    }

    private fun isMoviesLoaded(movies: List<Movie> = arrayListOf()) {

        if (movies.isNotEmpty()) {

            rv_favorite_movie.setVisibility()
            mAdapter.update(movies)
        } else {

            rv_favorite_movie.setVisibility(visible = false)
        }
    }

    override fun onMovieClickItem(movie: Movie, imgPoster: ImageView) {

        val intent = Intent(activity, MovieDetailActivity::class.java)
        intent.run {

            putExtra(MovieDetailActivity.EXTRA_MOVIE, movie)
            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, imgPoster, getString(R.string.tn_poster))
            startActivityForResult(intent, REQUEST_CODE, option.toBundle())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.movie_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_movie_refresh) {

            favoriteMovieVM.getFavoriteMovies()
            rv_favorite_movie.smoothScrollToPosition( 0)
            return true
        }

        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {

            if (resultCode == MovieDetailActivity.RESULT_CODE) {

                favoriteMovieVM.getFavoriteMovies()
            }
        }
    }

    override fun onDetach() {

        favoriteMovieVM.favoriteMovieListState.removeObserver(movieListStateObserver)
        super.onDetach()
    }
}
