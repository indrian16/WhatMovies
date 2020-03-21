package io.indrian.whatmovies.ui.searchtvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.ajalt.timberkt.d
import io.indrian.whatmovies.R
import io.indrian.whatmovies.adapter.TVShowAdapter
import io.indrian.whatmovies.data.model.TVShow
import io.indrian.whatmovies.ui.tvshowdetail.TVShowDetailActivity
import io.indrian.whatmovies.utils.setVisibility
import io.indrian.whatmovies.utils.showToast
import kotlinx.android.synthetic.main.fragment_search_tvshow.*
import kotlinx.android.synthetic.main.fragment_tvshow.rv_tv_show
import kotlinx.android.synthetic.main.fragment_tvshow.shimmer_tv_show_container
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchTVShowFragment : Fragment(), TVShowAdapter.OnTVShowClickCallBack {

    companion object {

        private const val EXTRA_QUERY = "extra_query"

        fun newInstance(query: String?) = SearchTVShowFragment().apply {

            arguments = Bundle().apply { putString(EXTRA_QUERY, query) }
        }
    }

    private val searchTVShowVM: SearchTVShowVM by viewModel { parametersOf(Bundle()) }
    private val mAdapter = TVShowAdapter(this)

    private val searchMovieStateObserver = Observer<SearchTVShowState> { state ->

        when (state) {

            is SearchTVShowState.InitState -> {

                d { "SearchTVShowState.InitState" }

                hideLoading()
                empty_tv_show_state.setVisibility(false)
                rv_tv_show.setVisibility(false)
            }

            is SearchTVShowState.Loading -> {

                d { "SearchTVShowState.Loading" }

                showLoading()
                empty_tv_show_state.setVisibility(false)
                rv_tv_show.setVisibility(false)
            }

            is SearchTVShowState.Error -> {

                d { "SearchTVShowState.Error" }

                showToast(state.message)
                hideLoading()
                empty_tv_show_state.setVisibility(false)
                rv_tv_show.setVisibility(false)
            }

            is SearchTVShowState.Loaded -> {

                d { "SearchTVShowState.Loaded" }

                hideLoading()
                empty_tv_show_state.setVisibility(false)
                rv_tv_show.setVisibility()

                mAdapter.updateItem(state.tvShowList)
            }

            is SearchTVShowState.NotFound -> {

                d { "SearchTVShowState.NotFound" }

                hideLoading()
                empty_tv_show_state.setVisibility()
                rv_tv_show.setVisibility(false)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_tvshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRv()

        val newQuery = arguments?.getString(EXTRA_QUERY) ?: ""
        d { newQuery }
        searchTVShowVM.searchTVShowState.observe(this, searchMovieStateObserver)
        if (newQuery.isNotBlank()) searchTVShowVM.searchTVShow(newQuery)
    }

    private fun setRv() {

        rv_tv_show.adapter = mAdapter
    }

    private fun showLoading() {

        shimmer_tv_show_container.startShimmer()
        shimmer_tv_show_container.setVisibility()
    }

    private fun hideLoading() {

        shimmer_tv_show_container.stopShimmer()
        shimmer_tv_show_container.setVisibility(false)
    }

    override fun onClickTVShow(tvShow: TVShow, imgPoster: ImageView) {

        val intent = Intent(activity, TVShowDetailActivity::class.java)
        intent.run {

            this.putExtra(TVShowDetailActivity.EXTRA_TV_SHOW, tvShow)
            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, imgPoster, getString(R.string.tn_poster))
            startActivity(intent, option.toBundle())
        }
    }

    override fun onDestroy() {

        searchTVShowVM.searchTVShowState.removeObserver(searchMovieStateObserver)
        super.onDestroy()
    }

}
