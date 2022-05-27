package com.example.challengechapter7.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengechapter7.R
import com.example.challengechapter7.adapter.AdapterFilmFavorite
import com.example.challengechapter7.roomdatabase.FilmFavoriteDatabase
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private var mdbFilmFavorite: FilmFavoriteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        mdbFilmFavorite = FilmFavoriteDatabase.getInstance(this)

    }

    fun getDataFilmFavorite(){
        rv_list_film_favorite.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        GlobalScope.launch {
            val listFavFilm = mdbFilmFavorite?.FilmFavoriteDao()?.getAllFilmFavorite()
            runOnUiThread{
                if (listFavFilm?.size != null) {
                    if(listFavFilm.isEmpty()){
                        tv_film_favorite_kosong.text = "Daftar Kosong!"
                    }else{
                        listFavFilm.let {
                            rv_list_film_favorite.adapter = AdapterFilmFavorite(it)
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getDataFilmFavorite()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}