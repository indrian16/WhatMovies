package io.indrian.moviecatalogue.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.data.model.Movie
import io.indrian.moviecatalogue.di.GlideApp
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieAdapter(private val listener: OnMovieClickListener): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var movies: List<Movie> = arrayListOf()

    fun update(newMovies: List<Movie>) {

        movies = newMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.movie_item, parent, false
            )
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(movie = movies[position])
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private var imgPoster: ImageView? = null

        fun bind(movie: Movie) = itemView.apply {

            imgPoster = this.findViewById(R.id.img_poster)
            GlideApp.with(this)
                .load(movie.poster)
                .into(img_poster)

            tv_title.text = movie.title
            tv_year.text = movie.year

        }.setOnClickListener { listener.onMovieClickItem(movie, imgPoster!!) }
    }

    interface OnMovieClickListener {

        fun onMovieClickItem(movie: Movie, imgPoster: ImageView)
    }
}