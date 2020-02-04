package io.indrian.moviecatalogue.ui.tvshow

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
import io.indrian.moviecatalogue.adapter.TVShowAdapter
import io.indrian.moviecatalogue.data.model.TVShow
import io.indrian.moviecatalogue.ui.tvshowdetail.TVShowDetailActivity
import io.indrian.moviecatalogue.utils.toVisible
import kotlinx.android.synthetic.main.fragment_tvshow.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class TVShowFragment : Fragment(), TVShowAdapter.OnTVShowClickCallBack {

    companion object {

        fun newInstance() = TVShowFragment()
    }

    private val tvShowVM: TVShowVM by viewModel()
    private val mAdapter = TVShowAdapter(this)

    private val tvShowListStateObserver = Observer<TVShowListState> { state ->

        when (state) {

            is TVShowListState.Loading -> {

                Timber.d { "TVShowLoading" }
                startShimmer()
                isTVShowLoaded()
            }

            is TVShowListState.Error -> {

                Timber.d { "TVShowError" }
                stopShimmer()
                isTVShowLoaded()
            }

            is TVShowListState.Loaded -> {

                Timber.d { "TVShowLoaded" }
                stopShimmer()
                isTVShowLoaded(state.tvShows)
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
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupVM()

        if (savedInstanceState == null ||
            tvShowVM.tvShowListState.value !is TVShowListState.Loaded
        ) {

            tvShowVM.getTVShows()
        }

    }

    private fun setupView() {

        rv_tv_show.apply {

            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }
    }

    private fun setupVM() {

        activity?.let { tvShowVM.tvShowListState.observe(it, tvShowListStateObserver) }
    }

    override fun onClickTVShow(tvShow: TVShow, imgPoster: ImageView) {

        val intent = Intent(activity, TVShowDetailActivity::class.java)
        intent.run {

            this.putExtra(TVShowDetailActivity.EXTRA_TV_SHOW, tvShow)
            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, imgPoster, getString(R.string.tn_poster))
            startActivity(intent, option.toBundle())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.tv_show_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_tv_show_refresh) {

            tvShowVM.getTVShows()
            rv_tv_show.smoothScrollToPosition( 0)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDetach() {

        tvShowVM.tvShowListState.removeObserver(tvShowListStateObserver)
        super.onDetach()
    }

    private fun startShimmer() {

        shimmer_tv_show_container.startShimmer()
        shimmer_tv_show_container.toVisible(visible = true)
    }

    private fun stopShimmer() {

        shimmer_tv_show_container.stopShimmer()
        shimmer_tv_show_container.toVisible(visible = false)
    }

    private fun isTVShowLoaded(movies: List<TVShow> = arrayListOf()) {

        if (movies.isNotEmpty()) {

            rv_tv_show.toVisible(visible = true)
            mAdapter.updateItem(movies)
        } else {

            rv_tv_show.toVisible(visible = false)
        }
    }
}
