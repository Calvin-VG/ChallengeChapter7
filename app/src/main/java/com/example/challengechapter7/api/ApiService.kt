package com.example.challengechapter7.api

import com.example.challengechapter7.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("film")
    suspend fun getAllDataFilm() : List<FilmResponseItem>

    @PUT("film")
    fun postDataFilm(postFilmResponse: PostFilmResponse): Call<FilmResponseItem>

    @GET("user")
    fun getAllDataUser(): Call<List<UserResponseItem>>

    @PUT("user/{id}")
    @FormUrlEncoded
    fun updateUser(
        @Path("id")id : String,
        @Field("name")name : String,
        @Field("pass")pass :String,
        @Field("username")username : String,
        @Field("address")adress : String,
        @Field("umur")umur : String,
        @Field("image")image : String
    ): Call<PostUserResponse>

    @POST("user")
    fun detailUser(@Field("id") id : Int) : Call<List<UserResponseItem>>

    @POST("user")
    fun postDataUser(@Body reqUser: PostUserResponse) : Call<UserResponseItem>

}