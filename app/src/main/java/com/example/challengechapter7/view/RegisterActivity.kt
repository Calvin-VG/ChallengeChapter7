package com.example.challengechapter7.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.challengechapter7.R
import com.example.challengechapter7.datastore.UserManager
import com.example.challengechapter7.viewmodel.ViewModelUser
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    lateinit var viewModelUserApi : ViewModelUser
    lateinit var userManager : UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userManager = UserManager(this)

        btn_register.setOnClickListener {
            val nama = et_register_nama.text.toString()
            val uname = et_register_username.text.toString()
            val umur = et_register_umur.text.toString().toInt()
            val alamat = et_register_alamat.text.toString()
            val image =  "http://loremflickr.com/640/480"
            val pass = et_register_password.text.toString()
            val pass_confirm = et_register_konfirmasi_password.text.toString()

            if (pass == pass_confirm){
                GlobalScope.launch {
                    addNewUser(alamat,image,umur,uname,pass,nama)
                }
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }else{
                Toast.makeText(this, "Password Yang Anda Masukkan Tidak Sama!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun addNewUser(alamat: String, image: String, umur: Int, username: String,
                           password: String, name: String){
        viewModelUserApi = ViewModelProvider(this).get(ViewModelUser::class.java)
        viewModelUserApi.addNewUserApi(alamat, image, umur, username, password, name)
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, "Registrasi User Berhasil!", Toast.LENGTH_SHORT).show()
        }
    }

}