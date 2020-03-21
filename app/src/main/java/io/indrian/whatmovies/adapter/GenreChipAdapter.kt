package io.indrian.whatmovies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.indrian.whatmovies.R
import io.indrian.whatmovies.data.model.Genre
import kotlinx.android.synthetic.main.genre_item.view.*

class GenreChipAdapter(private val onGenreCallBack: OnGenreCallBack) : RecyclerView.Adapter<GenreChipAdapter.ViewHolder>() {

    private var genres: List<Genre> = arrayListOf()

    fun addNewItem(newItems: List<Genre>) {

        genres = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.genre_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(genres[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(genre: Genre) {

            with(itemView) {

                genre_chip.text = genre.name
                itemView.setOnClickListener { onGenreCallBack.onClickItem(genre) }
            }
        }
    }

    interface OnGenreCallBack {

        fun onClickItem(genre: Genre)
    }
}