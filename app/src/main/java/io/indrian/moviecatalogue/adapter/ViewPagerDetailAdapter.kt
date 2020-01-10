package io.indrian.moviecatalogue.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.ui.cast.CastFragment
import io.indrian.moviecatalogue.ui.info.InfoFragment

class ViewPagerDetailAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(R.string.info, R.string.cast)

    override fun getItem(position: Int): Fragment {

        return when (position) {

            0 -> InfoFragment()
            1 -> CastFragment()
            else -> InfoFragment()
        }
    }

    override fun getCount(): Int = tabTitles.size

    override fun getPageTitle(position: Int): CharSequence? {

        return mContext.resources.getString(tabTitles[position])
    }
}