package io.indrian.moviefavorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var movies: List<Movie> = arrayListOf()

    fun updateMovie(movies: List<Movie>) {

        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(movies[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie) {

            with(itemView) {

                val date = Date(movie.date)
                Calendar.getInstance(Locale.getDefault()).also {

                    it.time = date
                    tv_year.text = it[Calendar.YEAR].toString()
                }

                tv_title.text = movie.title

                Glide.with(this)
                    .load(movie.poster)
                    .into(img_poster)

                setOnClickListener {

                    Toast.makeText(context, "You Click: ${movie.title}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}