package com.example.challengechapter7.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengechapter7.api.AppModule
import com.example.challengechapter7.model.UserResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelProfile: ViewModel() {

    lateinit var liveDataDetailUser: MutableLiveData<List<UserResponseItem>>

    init {
        liveDataDetailUser = MutableLiveData()
    }

    fun getLiveProfile(): MutableLiveData<List<UserResponseItem>> {
        return liveDataDetailUser
    }

    fun detailUserAPI(id : Int){
        AppModule.instance.detailUser(id)
            .enqueue(object : Callback<List<UserResponseItem>> {
                override fun onResponse(call: Call<List<UserResponseItem>>, response: Response<List<UserResponseItem>>) {
                    if (response.isSuccessful){
                        liveDataDetailUser.postValue(response.body())
                    }else{
                        liveDataDetailUser.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<UserResponseItem>>, t: Throwable) {
                    liveDataDetailUser.postValue(null)
                }

            })
    }
}