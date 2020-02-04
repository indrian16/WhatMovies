package io.indrian.moviecatalogue.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.adapter.FavoriteViewPagerAdapter
import io.indrian.moviecatalogue.ui.settings.SettingsActivity
import io.indrian.moviecatalogue.utils.showToast
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.toolbar.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        setupToolbar()
        setupViewPager()
    }

    private fun setupToolbar() {

        toolbar.apply {

            title = resources.getString(R.string.favorite)
        }.also {

            setSupportActionBar(it)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewPager() {

        val viewPagerAdapter = FavoriteViewPagerAdapter(
            this,
            supportFragmentManager
        )

        favorite_view_pager.adapter = viewPagerAdapter
        favorite_tabs.setupWithViewPager(favorite_view_pager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.favorite_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.action_setting -> {

                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }

            R.id.action_search -> {

                showToast(resources.getString(R.string.coming_soon))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {

        finish()
        return true
    }
}
