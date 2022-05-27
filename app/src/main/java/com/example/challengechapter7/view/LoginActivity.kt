package com.example.challengechapter7.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.challengechapter7.R
import com.example.challengechapter7.datastore.UserManager
import com.example.challengechapter7.model.UserResponseItem
import com.example.challengechapter7.viewmodel.ViewModelUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    lateinit var userManage : UserManager
    lateinit var listUserlogin : List<UserResponseItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userManage = UserManager(this)

        btn_login.setOnClickListener {
            Login()
        }

        tv_login_buat_akun.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

    fun Login(){
        val viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
        viewModel.loginUserAPI()
        viewModel.getLiveLogin().observe(this, Observer {
            if (it != null){
                listUserlogin = it
                loginAuth(listUserlogin)
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }else{
                Toast.makeText(this, "Gagal Login!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun loginAuth(listlogin : List<UserResponseItem>){
        userManage = UserManager(this)
        val username = et_login_username.text.toString()
        val  password = et_login_password.text.toString()
        for(i in listlogin.indices){
            if (username == listlogin[i].username && password == listlogin[i].password) {
                GlobalScope.launch {
                    userManage.setBoolean(true)
                    userManage.saveData(
                        listlogin[i].name,
                        listlogin[i].id,
                        listlogin[i].password,
                        listlogin[i].image,
                        listlogin[i].umur.toString(),
                        listlogin[i].username,
                        listlogin[i].address
                    )
                }
                Toast.makeText(this, "Welcome User!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}