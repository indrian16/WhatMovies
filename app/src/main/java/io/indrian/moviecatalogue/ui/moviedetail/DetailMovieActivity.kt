package io.indrian.moviecatalogue.ui.moviedetail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.data.model.Movie
import kotlinx.android.synthetic.main.activity_detail_movie.*
import java.util.*

class DetailMovieActivity : AppCompatActivity() {

    companion object {

        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        cardView.setCardBackgroundColor(Color.TRANSPARENT)
        intent.getParcelableExtra<Movie>(EXTRA_MOVIE)?.let { movie ->

            Glide.with(this)
                .load(movie.poster)
                .into(img_poster)
            Glide.with(this)
                .load(movie.backdrop)
                .into(img_backdrop)

            tv_year.text = movie.releaseDate[Calendar.YEAR].toString()
            tv_title.text = movie.title
            tv_overview.text = movie.overview
        }

    }
}
