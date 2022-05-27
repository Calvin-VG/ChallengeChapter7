package com.example.challengechapter7.view

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.challengechapter7.R
import com.example.challengechapter7.datastore.UserManager
import com.example.challengechapter7.viewmodel.ViewModelUser
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    lateinit var viewModelUserApi : ViewModelUser
    lateinit var usermanager : UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        usermanager = UserManager(this)

        getDataProfile()

        siv_profile_gambar.setOnClickListener {
            if (askForPermissions()) {
                setImage()
            }
        }

        btn_profile_update.setOnClickListener {
            updateDataUser()
        }

        btn_profile_logout.setOnClickListener {
            GlobalScope.launch {
                usermanager.hapusData()
            }
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    fun getDataProfile(){
        usermanager = UserManager(this)
        usermanager.Image.asLiveData().observe(this){
        }
        usermanager.Address.asLiveData().observe(this){
            et_profile_alamat.setText(it)
        }
        usermanager.Nama.asLiveData().observe(this){
            et_profile_nama.setText(it)
        }
        usermanager.Umur.asLiveData().observe(this){
            et_profile_umur.setText(it)
        }
        usermanager.userName.asLiveData().observe(this){
            et_profile_username.setText(it)
        }
        usermanager.Pass.asLiveData().observe(this){
            et_profile_password.setText(it)
        }
    }

    private fun updateDataUser() {
        usermanager = UserManager(this)

        var id = ""
        val nama = et_profile_nama.text.toString()
        val pass = et_profile_password.text.toString()
        val uname = et_profile_username.text.toString()
        val alamat = et_profile_alamat.text.toString()
        val umur = et_profile_umur.text.toString()
        val image =  usermanager.Image.toString()

        usermanager.Id.asLiveData().observe(this){
            id = it.toString()
        }
        AlertDialog.Builder(this)
            .setTitle("Update Data User")
            .setMessage("Yakin ingin mengupdate data?")
            .setNegativeButton("Tidak"){ dialogInterface : DialogInterface, i : Int ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("Ya"){ dialogInterface : DialogInterface, i : Int ->
                viewModelUserApi = ViewModelProvider(this).get(ViewModelUser::class.java)
                viewModelUserApi.updateUserAPI(id.toInt(),nama,pass,uname,alamat,umur,image)
                Toast.makeText(this, "Update data $nama berhasil", Toast.LENGTH_SHORT).show()
                GlobalScope.launch {
                    usermanager.saveData(
                        nama, id, pass, image, umur, uname, alamat
                    )
                }
                startActivity(Intent(this,ProfileActivity::class.java))
            }.show()
    }

    private fun setImage() {
        val inten = Intent(Intent.ACTION_PICK)
        inten.type = "image/*"
        startActivityForResult(inten, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode === RESULT_OK) {
            siv_profile_gambar.setImageURI(data?.data)
            GlobalScope.launch {
                usermanager.setImage(data?.data.toString())
            }
        }
    }

    fun isPermissionsAllowed(): Boolean {
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            false
        } else true
    }

    fun askForPermissions(): Boolean {
        if (!isPermissionsAllowed()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),2000)
            }
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            2000 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }else{
                }
                return
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton("App Settings",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    // send to app settings if permission is denied permanently
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", "and5.calvin.challengechapter7", null)
                    intent.data = uri
                    startActivity(intent)
                })
            .setNegativeButton("Cancel",null)
            .show()
    }

}