package io.indrian.moviecatalogue.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.ui.searchmovie.SearchMovieFragment
import io.indrian.moviecatalogue.ui.searchtvshow.SearchTVShowFragment

class SearchViewPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(R.string.movie, R.string.tv_show)

    override fun getItem(position: Int): Fragment {

        return when (position) {

            0 -> SearchMovieFragment()
            1 -> SearchTVShowFragment()

            else -> SearchMovieFragment()
        }
    }

    override fun getCount(): Int = tabTitles.size

    override fun getPageTitle(position: Int): CharSequence? {

        return mContext.resources.getString(tabTitles[position])
    }
}