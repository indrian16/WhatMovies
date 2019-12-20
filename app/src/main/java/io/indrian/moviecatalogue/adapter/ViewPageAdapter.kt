package io.indrian.moviecatalogue.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.ui.movie.MovieFragment
import io.indrian.moviecatalogue.ui.tvshow.TVShowFragment

class ViewPageAdapter(private val mContext: Context,fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(R.string.movie, R.string.tv_show)

    override fun getItem(position: Int): Fragment {

        return when (position) {

            0 -> MovieFragment.newInstance()
            1 -> TVShowFragment.newInstance()
            else -> MovieFragment.newInstance()
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {

        return mContext.resources.getString(tabTitles[position])
    }
}