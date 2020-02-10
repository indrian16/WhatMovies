package io.indrian.moviecatalogue.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import com.github.ajalt.timberkt.Timber.d
import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.adapter.SearchViewPagerAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.search_toolbar.*

class SearchActivity : AppCompatActivity() {

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

        val viewPagerAdapter = SearchViewPagerAdapter(
            this,
            supportFragmentManager
        )

        search_view_pager.adapter = viewPagerAdapter
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
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                d { "onQueryTextChange: $newText" }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}
