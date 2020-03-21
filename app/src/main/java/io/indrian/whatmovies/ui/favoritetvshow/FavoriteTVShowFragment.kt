package io.indrian.whatmovies.ui.favoritetvshow


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import com.github.ajalt.timberkt.Timber.d

import io.indrian.whatmovies.R
import io.indrian.whatmovies.adapter.TVShowAdapter
import io.indrian.whatmovies.data.model.TVShow
import io.indrian.whatmovies.ui.tvshowdetail.TVShowDetailActivity
import io.indrian.whatmovies.utils.showToast
import io.indrian.whatmovies.utils.setVisibility
import kotlinx.android.synthetic.main.fragment_favorite_tvshow.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FavoriteTVShowFragment : Fragment(), TVShowAdapter.OnTVShowClickCallBack {

    companion object {

        private const val REQUEST_CODE = 100
    }

    private val favoriteTVShowVM: FavoriteTVShowVM by viewModel { parametersOf(Bundle()) }
    private val mAdapter = TVShowAdapter(this)

    private val tvShowListStateObserver = Observer<FavoriteTVShowListState> { state ->

        when (state) {

            is FavoriteTVShowListState.Loading -> {

                d { "FavoriteTVShowListState.Loading" }
                startShimmer()
                isMoviesLoaded()
            }

            is FavoriteTVShowListState.Error -> {

                d { "FavoriteTVShowListState.Error" }
                showToast("Error: ${state.message}")
                stopShimmer()
                isMoviesLoaded()
            }

            is FavoriteTVShowListState.Loaded -> {

                d { "FavoriteTVShowListState.Loaded" }
                stopShimmer()
                isMoviesLoaded(state.tvShowList)
            }

            is FavoriteTVShowListState.Empty -> {

                d { "FavoriteTVShowListState.Empty" }
                stopShimmer()
                empty_tv_show_state.setVisibility(true)
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
        return inflater.inflate(R.layout.fragment_favorite_tvshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
        setupViewModel()

        if (savedInstanceState == null) favoriteTVShowVM.getFavoriteTVShow()
    }

    private fun setupRv() {

        rv_favorite_tv_show.adapter = mAdapter
    }

    private fun setupViewModel() {

        favoriteTVShowVM.favoriteTVShowListState.observe(activity!!, tvShowListStateObserver)
    }

    private fun startShimmer() {

        shimmer_favorite_tv_show_container.startShimmer()
        shimmer_favorite_tv_show_container.setVisibility()

        rv_favorite_tv_show.setVisibility(visible = false)
        empty_tv_show_state.setVisibility(visible = false)
    }

    private fun stopShimmer() {

        shimmer_favorite_tv_show_container.stopShimmer()
        shimmer_favorite_tv_show_container.setVisibility(visible = false)
    }

    private fun isMoviesLoaded(movies: List<TVShow> = arrayListOf()) {

        if (movies.isNotEmpty()) {

            rv_favorite_tv_show.setVisibility()
            mAdapter.updateItem(movies)
        } else {

            rv_favorite_tv_show.setVisibility(visible = false)
        }
    }

    override fun onClickTVShow(tvShow: TVShow, imgPoster: ImageView) {

        val intent = Intent(activity, TVShowDetailActivity::class.java)
        intent.run {

            putExtra(TVShowDetailActivity.EXTRA_TV_SHOW, tvShow)
            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, imgPoster, getString(R.string.tn_poster))
            startActivityForResult(intent, REQUEST_CODE, option.toBundle())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.tv_show_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_tv_show_refresh) {

            favoriteTVShowVM.getFavoriteTVShow()
            rv_favorite_tv_show.smoothScrollToPosition(0)
            return true
        }

        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {

            if (resultCode == TVShowDetailActivity.RESULT_CODE) {

                favoriteTVShowVM.getFavoriteTVShow()
            }
        }
    }

    override fun onDetach() {

        favoriteTVShowVM.favoriteTVShowListState.removeObserver(tvShowListStateObserver)
        super.onDetach()
    }
}
