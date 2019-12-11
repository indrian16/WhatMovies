package io.indrian.moviecatalogue.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.ui.movie.MovieFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupToolbar()
        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MovieFragment.newInstance(), MovieFragment::class.java.simpleName)
                .commitNowAllowingStateLoss()
        }
    }

    private fun setupToolbar() {

        setSupportActionBar(toolbar).apply {

            title = getString(R.string.app_name)
        }
    }

}
