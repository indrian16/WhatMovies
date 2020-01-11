package io.indrian.moviecatalogue.ui.movieinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.github.ajalt.timberkt.d

import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.data.model.MovieDetail
import io.indrian.moviecatalogue.utils.showToast
import io.indrian.moviecatalogue.utils.toVisible
import kotlinx.android.synthetic.main.fragment_movie_info.*
import org.koin.android.ext.android.inject

class MovieInfoFragment : Fragment() {

    companion object {

        private const val EXTRA_ID = "extra_id"

        fun newInstance(id: Int) = MovieInfoFragment().apply {

            val bundle = Bundle()
            bundle.putInt(EXTRA_ID, id)
            arguments = bundle
        }
    }

    private val viewMode: MovieInfoVM by inject()

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

        arguments?.getInt(EXTRA_ID)?.let { viewMode.getMovieDetail(it) }
        viewMode.movieDetailState.observe(this, movieDetailStateObserver)
    }

    private fun startLoading() {

        shimmer_overview.toVisible()
    }

    private fun stopLoading() {

        shimmer_overview.toVisible(false)
    }

    private fun loadedMovieDetail(movieDetail: MovieDetail) {

        tv_overview.text = movieDetail.overview
    }
    private fun errorState(message: String) {

        showToast("Error: $message")
    }

    override fun onDetach() {

        viewMode.movieDetailState.removeObserver(movieDetailStateObserver)
        super.onDetach()
    }
}
