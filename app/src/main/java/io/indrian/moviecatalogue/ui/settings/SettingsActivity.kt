package io.indrian.moviecatalogue.ui.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.ui.main.MainActivity
import io.indrian.moviecatalogue.utils.Constant
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.ext.android.inject

class SettingsActivity : AppCompatActivity() {

    private val viewModel: SettingsVM by inject()

    private val languageStateObserver = Observer<LanguageState> { state ->

        when (state) {

            is LanguageState.English -> {

                rg_language.check(R.id.rb_en)
            }

            is LanguageState.Indonesian -> {

                rg_language.check(R.id.rb_id)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setupToolbar()
        setListener()
        setViewModel()
    }

    private fun setViewModel() {

        viewModel.getLanguageSettings()
        viewModel.languageState.observe(this, languageStateObserver)
    }

    private fun setListener() {

        rg_language.setOnCheckedChangeListener { group, _ ->

            when (group.checkedRadioButtonId) {

                R.id.rb_en -> {

                    viewModel.changeLanguage(baseContext, Constant.DEFAULT_LANGUAGE)
                }

                R.id.rb_id -> {

                    viewModel.changeLanguage(baseContext, Constant.ID)
                }
            }
        }

        btn_restart_app.setOnClickListener {

            finishAffinity()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setupToolbar() {

        setSupportActionBar(toolbar).apply {

            title = getString(R.string.settings)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {

        finish()
        return true
    }

    override fun onDestroy() {

        viewModel.languageState.removeObserver(languageStateObserver)
        super.onDestroy()
    }
}
