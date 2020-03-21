package io.indrian.whatmovies.ui.searchmovie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.ajalt.timberkt.Timber.d
import io.indrian.whatmovies.R
import io.indrian.whatmovies.adapter.MovieAdapter
import io.indrian.whatmovies.data.model.Movie
import io.indrian.whatmovies.ui.moviedetail.MovieDetailActivity
import io.indrian.whatmovies.utils.setVisibility
import io.indrian.whatmovies.utils.showToast
import kotlinx.android.synthetic.main.fragment_search_movie.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchMovieFragment : Fragment(), MovieAdapter.OnMovieClickCallback {

    companion object {

        private const val EXTRA_QUERY = "extra_query"

        fun newInstance(query: String?) = SearchMovieFragment().apply {

            arguments = Bundle().apply { putString(EXTRA_QUERY, query) }
        }
    }

    private val searchMovieVM: SearchMovieVM by viewModel { parametersOf(Bundle()) }
    private val mAdapter = MovieAdapter(this)

    private val searchMovieStateObserver = Observer<SearchMovieState> { state ->

        when (state) {

            is SearchMovieState.InitState -> {

                d { "SearchMovieState.InitState" }

                hideLoading()
                empty_movie_state.setVisibility(false)
                rv_movie.setVisibility(false)
            }

            is SearchMovieState.Loading -> {

                d { "SearchMovieState.Loading" }

                showLoading()
                empty_movie_state.setVisibility(false)
                rv_movie.setVisibility(false)
            }

            is SearchMovieState.Error -> {

                d { "SearchMovieState.Error" }

                showToast(state.message)
                hideLoading()
                empty_movie_state.setVisibility(false)
                rv_movie.setVisibility(false)
            }

            is SearchMovieState.Loaded -> {

                d { "SearchMovieState.Loaded" }

                hideLoading()
                empty_movie_state.setVisibility(false)
                rv_movie.setVisibility()

                mAdapter.update(state.movies)
            }

            is SearchMovieState.NotFound -> {

                d { "SearchMovieState.NotFound" }

                hideLoading()
                empty_movie_state.setVisibility()
                rv_movie.setVisibility(false)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRv()

        val newQuery = arguments?.getString(EXTRA_QUERY) ?: ""
        d { newQuery }
        searchMovieVM.searchMovieState.observe(this, searchMovieStateObserver)
        if (newQuery.isNotBlank()) searchMovieVM.searchMovie(newQuery)
    }

    private fun setRv() {

        rv_movie.adapter = mAdapter
    }

    private fun showLoading() {

        shimmer_movie_container.startShimmer()
        shimmer_movie_container.setVisibility()
    }

    private fun hideLoading() {

        shimmer_movie_container.stopShimmer()
        shimmer_movie_container.setVisibility(false)
    }

    override fun onMovieClickItem(movie: Movie, imgPoster: ImageView) {

        val intent = Intent(activity, MovieDetailActivity::class.java)
        intent.run {

            this.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie)
            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, imgPoster, getString(R.string.tn_poster))
            startActivity(intent, option.toBundle())
        }
    }

    override fun onDestroy() {

        searchMovieVM.searchMovieState.removeObserver(searchMovieStateObserver)
        super.onDestroy()
    }
}
