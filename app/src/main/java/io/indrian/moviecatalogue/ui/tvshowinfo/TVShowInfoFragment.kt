package io.indrian.moviecatalogue.ui.tvshowinfo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.ajalt.timberkt.d
import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.data.model.TVShowDetail
import io.indrian.moviecatalogue.utils.showToast
import io.indrian.moviecatalogue.utils.visibility
import kotlinx.android.synthetic.main.fragment_tvshow_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TVShowInfoFragment : Fragment() {

    companion object {

        private const val EXTRA_ID = "extra_id"

        fun newInstance(id: Int) = TVShowInfoFragment().apply {

            val bundle = Bundle()
            bundle.putInt(EXTRA_ID, id)
            arguments = bundle
        }
    }

    private val tvShowInfoVM: TVShowInfoVM by viewModel { parametersOf(Bundle()) }

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

        if (savedInstanceState == null) {

            arguments?.getInt(EXTRA_ID)?.let { tvShowInfoVM.getTVShowDetail(it) }
        }

        tvShowInfoVM.tvShowDetailState.observe(this, tvShowDetailStateObServer)
    }

    private fun startLoading() {

        shimmer_overview.visibility()
    }

    private fun stopLoading() {

        shimmer_overview.visibility(false)
    }

    private fun loadedMovieDetail(tvShowDetail: TVShowDetail) {

        tv_overview.text = tvShowDetail.overview
    }
    private fun errorState(message: String) {

        showToast("Error: $message")
    }

    override fun onDetach() {

        tvShowInfoVM.tvShowDetailState.removeObserver(tvShowDetailStateObServer)
        super.onDetach()
    }
}
