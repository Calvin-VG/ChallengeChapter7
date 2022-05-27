package com.example.challengechapter7.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengechapter7.api.AppModule
import com.example.challengechapter7.model.FilmResponseItem
import com.example.challengechapter7.model.PostFilmResponse
import com.example.challengechapter7.model.PostUserResponse
import com.example.challengechapter7.model.UserResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelFilmAdmin: ViewModel() {

    private var liveDataAddFilm = MutableLiveData<List<FilmResponseItem>>()

    init{
        liveDataAddFilm = MutableLiveData()
    }

    fun getLiveDataAddFilm(): MutableLiveData<List<FilmResponseItem>>{
        return liveDataAddFilm
    }

    fun addNewFilmApi(tanggal: String, deskripsi: String, sutradara: String,
                      gambar: String, judul: String): Boolean{
        var messageResponse = false
        AppModule.instance.postDataFilm(
            PostFilmResponse(tanggal, deskripsi, sutradara, gambar, judul)
        ).enqueue(object : Callback<FilmResponseItem> {
            override fun onResponse(
                call: Call<FilmResponseItem>,
                response: Response<FilmResponseItem>
            ) {
                messageResponse = response.isSuccessful
            }

            override fun onFailure(call: Call<FilmResponseItem>, t: Throwable) {
                messageResponse = false
            }
        })
        return messageResponse
    }

}