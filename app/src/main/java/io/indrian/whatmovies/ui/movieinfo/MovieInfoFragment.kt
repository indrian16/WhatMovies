package io.indrian.whatmovies.ui.movieinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.ajalt.timberkt.d
import io.indrian.whatmovies.R
import io.indrian.whatmovies.data.model.MovieDetail
import io.indrian.whatmovies.utils.showToast
import io.indrian.whatmovies.utils.setVisibility
import kotlinx.android.synthetic.main.fragment_movie_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieInfoFragment : Fragment() {

    companion object {

        private const val EXTRA_ID = "extra_id"

        fun newInstance(id: Int) = MovieInfoFragment().apply {

            val bundle = Bundle()
            bundle.putInt(EXTRA_ID, id)
            arguments = bundle
        }
    }

    private val movieInfoVM: MovieInfoVM by viewModel { parametersOf(Bundle()) }

    private val movieDetailStateObserver = Observer<MovieDetailState> { state ->

        when (state) {

            is MovieDetailState.Loading -> {

                d { "MovieDetailState.Loading" }
                startLoading()
            }
            is MovieDetailState.Error -> {

                d { "MovieDetailState.Error" }
                stopLoading()
                errorState(state.message)
            }
            is MovieDetailState.Loaded -> {

                d { "MovieDetailState.Loaded" }
                stopLoading()
                loadedMovieDetail(state.movieDetail)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {

            arguments?.getInt(EXTRA_ID)?.let { movieInfoVM.getMovieDetail(it) }
        }

        movieInfoVM.movieDetailState.observe(this, movieDetailStateObserver)
    }

    private fun startLoading() {

        shimmer_overview.setVisibility()
    }

    private fun stopLoading() {

        shimmer_overview.setVisibility(false)
    }

    private fun loadedMovieDetail(movieDetail: MovieDetail) {

        tv_overview.text = movieDetail.overview
    }
    private fun errorState(message: String) {

        showToast("Error: $message")
    }

    override fun onDetach() {

        movieInfoVM.movieDetailState.removeObserver(movieDetailStateObserver)
        super.onDetach()
    }
}
