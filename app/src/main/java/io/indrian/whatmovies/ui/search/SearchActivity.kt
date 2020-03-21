package io.indrian.whatmovies.ui.search

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import com.github.ajalt.timberkt.Timber.d
import io.indrian.whatmovies.R
import io.indrian.whatmovies.adapter.SearchViewPagerAdapter
import io.indrian.whatmovies.ui.searchmovie.SearchMovieFragment
import io.indrian.whatmovies.ui.searchtvshow.SearchTVShowFragment
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.search_toolbar.*

class SearchActivity : AppCompatActivity() {

    private val viewPagerAdapter = SearchViewPagerAdapter(this, supportFragmentManager)
    private val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupToolbar()
        setupViewPager()
    }

    private fun setupToolbar() {

        setSupportActionBar(search_toolbar).apply { title = "" }
    }

    private fun setupViewPager() {

        search_view_pager.adapter = viewPagerAdapter
        viewPagerAdapter.changeQueryFragment(arrayListOf(
            SearchMovieFragment.newInstance(""),
            SearchTVShowFragment.newInstance("")
        ))
        search_tabs.setupWithViewPager(search_view_pager)
    }

    @Suppress("DEPRECATION")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        searchItem?.expandActionView()
        MenuItemCompat.setOnActionExpandListener(searchItem, object : MenuItemCompat.OnActionExpandListener {

            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {

                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {

                finish()
                return true
            }
        })

        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                d { "onQueryTextSubmit: $query" }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                mHandler.removeCallbacksAndMessages(null)
                mHandler.postDelayed({


                    d { "onQueryTextChange: $newText" }
                    viewPagerAdapter.changeQueryFragment(arrayListOf(
                        SearchMovieFragment.newInstance(newText),
                        SearchTVShowFragment.newInstance(newText)
                    ))

                }, 500L)
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}
