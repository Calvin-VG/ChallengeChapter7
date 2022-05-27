package com.example.challengechapter7.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.challengechapter7.R
import com.example.challengechapter7.model.FilmResponseItem
import com.example.challengechapter7.roomdatabase.FilmFavorite
import com.example.challengechapter7.roomdatabase.FilmFavoriteDatabase
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class DetailActivity : AppCompatActivity() {

    var filmdb: FilmFavoriteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        filmdb = FilmFavoriteDatabase.getInstance(this)

        val detailFilm = intent.getParcelableExtra<FilmResponseItem>("DETAIL_FILM")

        tv_detail_judul_film.text = detailFilm?.name
        tv_detail_tahun_film.text = detailFilm?.date
        tv_detail_sutradara_film.text = detailFilm?.director
        tv_detail_deskripsi_film.text = detailFilm?.description
        Glide.with(this).load(detailFilm?.image).into(siv_detail_gambar_film)

        fab_favorite.setOnClickListener {
            addFilmFavorite()
        }

    }

    fun addFilmFavorite(){
        val detailFilm = intent.getParcelableExtra<FilmResponseItem>("DETAIL_FILM")

        GlobalScope.async {
            val director = detailFilm!!.director
            val releasedate = detailFilm!!.date
            val synopsis = detailFilm!!.description
            val title = detailFilm!!.name
            val image = detailFilm!!.image

            filmdb?.FilmFavoriteDao()?.insertFilmFavorite(FilmFavorite(null, director, image, releasedate, synopsis, title))

        }
        startActivity(Intent(this, FavoriteActivity::class.java))
    }

}