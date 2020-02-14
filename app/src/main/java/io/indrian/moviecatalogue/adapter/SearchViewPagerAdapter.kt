package io.indrian.moviecatalogue.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import io.indrian.moviecatalogue.R

class SearchViewPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragments: List<Fragment> = arrayListOf()

    fun changeQueryFragment(newQueryFragment: List<Fragment>) {

        fragments = newQueryFragment
        notifyDataSetChanged()
    }

    override fun getItemPosition(`object`: Any): Int {

        return POSITION_NONE
    }

    @StringRes
    private val tabTitles = intArrayOf(R.string.movie, R.string.tv_show)

    override fun getItem(position: Int): Fragment {

        return fragments[position]
    }

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? {

        return mContext.resources.getString(tabTitles[position])
    }

}