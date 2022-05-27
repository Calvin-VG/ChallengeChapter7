package com.example.challengechapter7.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FilmFavoriteDao {

    @Insert
    fun insertFilmFavorite(filmfavorite: FilmFavorite) : Long

    @Query("SELECT * FROM FilmFavorite")
    fun getAllFilmFavorite() : List<FilmFavorite>

    @Delete
    fun deleteFilmFavorite(filmfavorite: FilmFavorite) : Int

}