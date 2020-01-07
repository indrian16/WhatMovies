package io.indrian.moviecatalogue.ui.tvshowdetail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.data.model.TVShow
import kotlinx.android.synthetic.main.activity_tvshow_detail.*
import java.util.*

class TVShowDetailActivity : AppCompatActivity() {

    companion object {

        const val EXTRA_TV_SHOW = "extra_tv_show"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_detail)

        cardView.setCardBackgroundColor(Color.TRANSPARENT)
        intent.getParcelableExtra<TVShow>(EXTRA_TV_SHOW)?.let { tvShow ->

            Glide.with(this)
                .load(tvShow.poster)
                .into(img_poster)
            Glide.with(this)
                .load(tvShow.backdrop)
                .into(img_backdrop)

            tv_year.text = tvShow.releaseDate[Calendar.YEAR].toString()
            tv_name.text = tvShow.name
            tv_overview.text = tvShow.overview
        }
    }
}
