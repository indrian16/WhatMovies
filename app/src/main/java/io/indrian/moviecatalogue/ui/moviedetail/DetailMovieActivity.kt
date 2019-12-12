package io.indrian.moviecatalogue.ui.moviedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.data.model.Movie
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {

    companion object {

        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        intent.getParcelableExtra<Movie>(EXTRA_MOVIE)?.let { movie ->

            Glide.with(this)
                .load(movie.poster)
                .into(img_poster)
            Glide.with(this)
                .load(movie.backdrop)
                .into(img_backdrop)

            tv_year.text = movie.year
            tv_title.text = movie.title
            tv_overview.text = movie.overview
        }

    }
}