package com.example.challengechapter7.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengechapter7.api.ApiService
import com.example.challengechapter7.model.FilmResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelFilm @Inject constructor(api: ApiService): ViewModel() {

//    @Inject
//    lateinit var dataNews : ApiService

    private var liveDataFilm = MutableLiveData<List<FilmResponseItem>>()
    val film : LiveData<List<FilmResponseItem>> = liveDataFilm

    init {
        viewModelScope.launch {
            val dataFilm = api.getAllDataFilm()
            delay(2000)
            liveDataFilm.value = dataFilm
        }
    }

}