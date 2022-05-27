package com.example.challengechapter7.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengechapter7.R
import com.example.challengechapter7.model.FilmResponseItem
import com.example.challengechapter7.roomdatabase.FilmFavorite
import com.example.challengechapter7.roomdatabase.FilmFavoriteDatabase
import com.example.challengechapter7.view.FavoriteActivity
import kotlinx.android.synthetic.main.item_film_adapter.view.*
import kotlinx.android.synthetic.main.item_film_favorite_adapter.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AdapterFilmFavorite(val filmDataFavorite: List<FilmFavorite>): RecyclerView.Adapter<AdapterFilmFavorite.ViewHolder>() {
    private var databaseFilmFavorite: FilmFavoriteDatabase? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewitem = LayoutInflater.from(parent.context).inflate(R.layout.item_film_favorite_adapter,parent, false)
        return ViewHolder(viewitem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_item_judul_film_favorite.text = filmDataFavorite!![position].title
        holder.itemView.tv_item_tanggal_rilis_film_favorite.text = filmDataFavorite!![position].releaseDate
        holder.itemView.tv_item_sutradara_film_favorite.text = filmDataFavorite!![position].director

        Glide.with(holder.itemView.context).load(filmDataFavorite!![position].image).into(holder.itemView.siv_item_gambar_favorite)

        holder.itemView.iv_delete_favorite.setOnClickListener {
            databaseFilmFavorite = FilmFavoriteDatabase.getInstance(it.context)
            AlertDialog.Builder(it.context)
                .setTitle("Hapus Data Favorite")
                .setMessage("Yakin ingin menghapus favorite?")
                .setPositiveButton("Ya"){
                        dialogInterface : DialogInterface, i : Int ->
                    GlobalScope.async {
                        val result = databaseFilmFavorite?.FilmFavoriteDao()?.deleteFilmFavorite(
                            filmDataFavorite!![position])
                        (holder.itemView.context as FavoriteActivity).runOnUiThread{
                            if (result != 0){
                                Toast.makeText(it.context, "Data ${filmDataFavorite!![position].title} dihapus",
                                    Toast.LENGTH_LONG).show()
                                (holder.itemView.context as FavoriteActivity).getDataFilmFavorite()
                            }else{
                                Toast.makeText(it.context, "Gagal menghapus", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
                .setNegativeButton("Tidak"){
                        dialogInterface : DialogInterface, i : Int ->
                    dialogInterface.dismiss()
                }
                .show()
        }

    }

    override fun getItemCount(): Int {
        return filmDataFavorite!!.size
    }


}