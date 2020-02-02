package io.indrian.moviecatalogue.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.ui.favorite.FavoriteActivity
import io.indrian.moviecatalogue.ui.settings.SettingsActivity
import io.indrian.moviecatalogue.utils.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity: AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
        if (savedInstanceState == null) {

            setupBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {

        val navGraphIds = listOf(
            R.navigation.movie_navigation,
            R.navigation.tv_show_navigation,
            R.navigation.discover_navigation
        )

        val controller = bottom_nav.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.main_nav_host_container,
            intent = intent
        )

        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    private fun setupToolbar() {

        toolbar.apply {

            this.overflowIcon?.setTint(ContextCompat.getColor(baseContext, R.color.colorWhite))

        }.also {

            setSupportActionBar(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.action_setting -> {

                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }

            R.id.action_search -> {

                return true
            }

            R.id.action_favorite -> {

                startActivity(Intent(this, FavoriteActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {

        return currentNavController?.value?.navigateUp() ?: false
    }
}