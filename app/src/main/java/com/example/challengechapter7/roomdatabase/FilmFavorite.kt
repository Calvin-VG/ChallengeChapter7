package com.example.challengechapter7.roomdatabase

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class FilmFavorite(
    @PrimaryKey(autoGenerate = true)
    val id : Int?,
    @ColumnInfo(name = "director")
    val director: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "synopsis")
    val synopsis: String,
    @ColumnInfo(name = "title")
    val title: String
): Parcelable