package io.indrian.moviecatalogue.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.indrian.moviecatalogue.R

class ViewPagerDetailAdapter(
    private val mContext: Context,
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(R.string.info, R.string.cast)

    private var fragmentList: List<Fragment> = arrayListOf()

    fun addPages(pages: List<Fragment>) {

        fragmentList = pages
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {

        return fragmentList[position]
    }

    override fun getCount(): Int = tabTitles.size

    override fun getPageTitle(position: Int): CharSequence? {

        return mContext.resources.getString(tabTitles[position])
    }
}