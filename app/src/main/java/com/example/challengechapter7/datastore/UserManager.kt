package com.example.challengechapter7.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {

    private val dataStore : DataStore<Preferences> = context.createDataStore(name = "user_pref")
    private val imageData : DataStore<Preferences> = context.createDataStore(name = "image")

    companion object{
        val NAMA = preferencesKey<String>("nama")
        val PASS = preferencesKey<String>("pass")
        val IMAGE = preferencesKey<String>("IMAGE")
        val ID = preferencesKey<String>("id")
        val UMUR = preferencesKey<String>("umur")
        val USERNAME = preferencesKey<String>("username")
        val ADDRESS = preferencesKey<String>("ADDRESS")
        val BOOLEAN = preferencesKey<Boolean>("BOOLEAN")
    }

    suspend fun saveData(nama: String, id:String,pass: String, image: String, umur:String,username:String,adress:String){
        dataStore.edit {
            it[NAMA]= nama
            it[ID]= id
            it[PASS]= pass
            it[IMAGE] = image
            it[UMUR]= umur
            it[USERNAME]= username
            it[ADDRESS]= adress
        }
    }
    suspend fun setImage(image: String){
        imageData.edit {
            it[IMAGE] = image

        }
    }
    val Nama : Flow<String> = dataStore.data.map {
        it[NAMA] ?:""
    }
    val Id : Flow<String> = dataStore.data.map {
        it[ID] ?:""
    }
    val Pass : Flow<String> = dataStore.data.map {
        it[PASS] ?:""
    }
    val Umur : Flow<String> = dataStore.data.map {
        it[UMUR] ?:""
    }
    val userName : Flow<String> = dataStore.data.map {
        it[USERNAME] ?:""
    }
    val Image : Flow<String> = dataStore.data.map {
        it[IMAGE] ?:""
    }
    val Address : Flow<String> = dataStore.data.map {
        it[ADDRESS] ?:""
    }
    val ceklogin : Flow<Boolean> = dataStore.data.map {
        it[BOOLEAN] ?: false
    }
    suspend fun hapusData(){
        dataStore.edit {
            it.clear()
        }

    }
    suspend fun setBoolean(boolean: Boolean){
        dataStore.edit {
            it[BOOLEAN] = boolean
        }
    }

}