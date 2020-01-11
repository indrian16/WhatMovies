package io.indrian.moviecatalogue.ui.tvshowinfo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.github.ajalt.timberkt.d

import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.data.model.TVShowDetail
import io.indrian.moviecatalogue.utils.showToast
import io.indrian.moviecatalogue.utils.toVisible
import kotlinx.android.synthetic.main.fragment_tvshow_info.*
import org.koin.android.ext.android.inject

class TVShowInfoFragment : Fragment() {

    companion object {

        private const val EXTRA_ID = "extra_id"

        fun newInstance(id: Int) = TVShowInfoFragment().apply {

            val bundle = Bundle()
            bundle.putInt(EXTRA_ID, id)
            arguments = bundle
        }
    }

    private val viewModel: TVShowInfoVM by inject()

    private val tvShowDetailStateObServer = Observer<TVShowDetailState> { state ->

        when (state) {

            is TVShowDetailState.Loading -> {

                d { "TVShowDetailState.Loading" }
                startLoading()
            }

            is TVShowDetailState.Error -> {

                d { "TVShowDetailState.Error" }
                stopLoading()
                errorState(state.message)
            }

            is TVShowDetailState.Loaded -> {

                d { "TVShowDetailState.Loaded" }
                stopLoading()
                loadedMovieDetail(state.tvShowDetail)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tvshow_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt(EXTRA_ID)?.let { viewModel.getTVShowDetail(it) }
        viewModel.tvShowDetailState.observe(this, tvShowDetailStateObServer)
    }

    private fun startLoading() {

        shimmer_overview.toVisible()
    }

    private fun stopLoading() {

        shimmer_overview.toVisible(false)
    }

    private fun loadedMovieDetail(tvShowDetail: TVShowDetail) {

        tv_overview.text = tvShowDetail.overview
    }
    private fun errorState(message: String) {

        showToast("Error: $message")
    }

    override fun onDetach() {

        viewModel.tvShowDetailState.removeObserver(tvShowDetailStateObServer)
        super.onDetach()
    }
}
