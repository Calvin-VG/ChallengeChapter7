package com.example.challengechapter7.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengechapter7.BuildConfig
import com.example.challengechapter7.R
import com.example.challengechapter7.adapter.AdapterFilm
import com.example.challengechapter7.datastore.UserManager
import com.example.challengechapter7.viewmodel.ViewModelFilm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.gratis.activity_home.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.iv_home_akun
import kotlinx.android.synthetic.main.activity_home.iv_home_favorite
import kotlinx.android.synthetic.main.activity_home.rv_list_film
import kotlinx.android.synthetic.main.activity_home.tv_home

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    lateinit var filmAdap: AdapterFilm
    lateinit var usermanage: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        usermanage = UserManager(this)
        usermanage.userName.asLiveData().observe(this) {
            tv_home.text = "Welcome, $it"
        }

        if(BuildConfig.FLAVOR.equals("admin")){
            fab_tambah_film.setOnClickListener {
                startActivity(Intent(this,AddFilmActivity::class.java))
            }
        }

        if(BuildConfig.FLAVOR.equals("gratis")){
            iv_home_favorite.setOnClickListener {
                startActivity(Intent(this,FavoriteActivity::class.java))
            }

            iv_home_akun.setOnClickListener {
                startActivity(Intent(this,ProfileActivity::class.java))
            }
        }

        getDataNews()


    }

    fun getDataNews(){
        val newsAdapter = AdapterFilm(){
            val pindah = Intent(this, DetailActivity::class.java)
            pindah.putExtra("DETAIL_FILM", it)
            startActivity(pindah)
        }
        rv_list_film.layoutManager = LinearLayoutManager(this)
        rv_list_film.adapter = newsAdapter
        val viewModel = ViewModelProvider(this).get(ViewModelFilm::class.java)
        viewModel.film.observe(this,{
            if (it != null){
                newsAdapter.setNewsList(it)
                newsAdapter.notifyDataSetChanged()
            }
        })
    }

}