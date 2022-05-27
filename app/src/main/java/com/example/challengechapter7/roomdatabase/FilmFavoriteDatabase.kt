package com.example.challengechapter7.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FilmFavorite::class], version = 1)
abstract class FilmFavoriteDatabase: RoomDatabase() {

    abstract fun FilmFavoriteDao() : FilmFavoriteDao

    companion object{
        private var INSTANCE : FilmFavoriteDatabase? = null
        fun getInstance(context : Context):FilmFavoriteDatabase? {
            if (INSTANCE == null){
                synchronized(FilmFavoriteDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FilmFavoriteDatabase::class.java,"FilmFavorite.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }

}