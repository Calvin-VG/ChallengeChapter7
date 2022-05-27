package com.example.challengechapter7.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.asLiveData
import com.example.challengechapter7.R
import com.example.challengechapter7.datastore.UserManager

class MainActivity : AppCompatActivity() {

    lateinit var userMana: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userMana = UserManager(this)
        Handler(Looper.getMainLooper()).postDelayed({
            userMana.ceklogin.asLiveData().observe(this){
                if(it == true){
                    startActivity(Intent(this, HomeActivity::class.java))
                }else{
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        },3000)

    }
}