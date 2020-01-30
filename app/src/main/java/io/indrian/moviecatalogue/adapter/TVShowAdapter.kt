package io.indrian.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.data.model.TVShow
import io.indrian.moviecatalogue.di.GlideApp
import kotlinx.android.synthetic.main.tv_show_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class TVShowAdapter(private val onTVShowClickCallBack: OnTVShowClickCallBack) : RecyclerView.Adapter<TVShowAdapter.ViewHolder>() {

    private var tvShowList: List<TVShow> = ArrayList()

    fun updateItem(tvShowList: List<TVShow>) {

        this.tvShowList = tvShowList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tv_show_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = tvShowList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(tvShowList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(tvShow: TVShow) {

            with(itemView) {

                val imgPoster = findViewById<ImageView>(R.id.img_poster)
                GlideApp.with(this)
                    .load(tvShow.poster)
                    .into(imgPoster)

                tv_name.text = tvShow.name
                tv_year.text = tvShow.year

                this.setOnClickListener { onTVShowClickCallBack.onClickTVShow(tvShow, imgPoster) }
            }
        }
    }

    interface OnTVShowClickCallBack {

        fun onClickTVShow(tvShow: TVShow, imgPoster: ImageView)
    }
}