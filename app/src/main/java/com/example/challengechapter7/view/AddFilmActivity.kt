package com.example.challengechapter7.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.challengechapter7.R
import com.example.challengechapter7.viewmodel.ViewModelFilmAdmin
import com.example.challengechapter7.viewmodel.ViewModelUser
import kotlinx.android.synthetic.main.activity_add_film.*

class AddFilmActivity : AppCompatActivity() {

    lateinit var viewModelFilmApi: ViewModelFilmAdmin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_film)

        btn_tambah_film.setOnClickListener {
            val tanggal = et_add_new_tanggal_film.text.toString()
            val deskrip = et_add_new_deskripsi_film.text.toString()
            val sutradata = et_add_new_sutradara_film.text.toString()
            val gambar = "http://loremflickr.com/640/480"
            val judul = et_add_new_judul_film.text.toString()

            tambahFilm(tanggal, deskrip, sutradata, gambar, judul)

        }

    }

    fun tambahFilm(tgl: String, desk: String, sut: String, img: String, tit: String) {
        viewModelFilmApi = ViewModelProvider(this).get(ViewModelFilmAdmin::class.java)
        viewModelFilmApi.addNewFilmApi(tgl, desk, sut, img, tit)
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, "Film $tit Berhasil Ditambahkan!", Toast.LENGTH_SHORT).show()
        }
    }
}