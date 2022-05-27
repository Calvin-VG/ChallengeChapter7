package com.example.challengechapter7.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengechapter7.R
import com.example.challengechapter7.model.FilmResponseItem
import kotlinx.android.synthetic.main.item_film_adapter.view.*

class AdapterFilm(private val onClick : (FilmResponseItem)->Unit): RecyclerView.Adapter<AdapterFilm.ViewHolder>() {
    private var filmData : List<FilmResponseItem>? = null

    fun setNewsList(newsList : List<FilmResponseItem>){
        this.filmData = newsList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFilm.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_film_adapter, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterFilm.ViewHolder, position: Int) {
        holder.itemView.tv_item_judul_film.text = filmData!![position].name
        holder.itemView.tv_item_tanggal_rilis_film.text = filmData!![position].date
        holder.itemView.tv_item_sutradara_film.text = filmData!![position].director

        Glide.with(holder.itemView.context).load(filmData!![position].image).into(holder.itemView.siv_item_gambar)

        holder.itemView.cv_list_film.setOnClickListener {
            onClick(filmData!![position])
        }
    }

    override fun getItemCount(): Int {
        if (filmData == null){
            return 0
        }else{
            return filmData!!.size
        }
    }
}