package com.example.roomdatabasemvvm

import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.room.mvvm.room.LoginDatabase
import com.example.room.mvvm.viewmodel.LoginViewModel
import com.example.roomdatabasemvvm.adapter.RecyclerAdapter
import com.example.roomdatabasemvvm.databinding.ActivityMainBinding
import com.example.roomdatabasemvvm.model.LoginTableModel
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var loginViewModel: LoginViewModel

    lateinit var database: LoginDatabase

    lateinit var adapter: RecyclerAdapter
    var userList = mutableListOf<LoginTableModel>()

    lateinit var strUsername: String
    lateinit var strPassword: String

    private val SECRET_KEY = "aesEncryptionKey"

    private val INIT_VECTOR = "encryptionIntVec"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = LoginDatabase.getDataseClient(this)

        adapter = RecyclerAdapter(this, userList)
        val manager = LinearLayoutManager(applicationContext)
        binding.rvRecyclerview.layoutManager = manager
        binding.rvRecyclerview.adapter = adapter
        updateList()

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.btnAddLogin.setOnClickListener {

            strUsername = binding.txtUsername.text.toString().trim()
            strPassword = binding.txtPassword.text.toString().trim()

            val encyName = encrypt(strUsername)
            val encyPassword = encrypt(strPassword)

            if (encyName != null) {
                if (encyPassword != null) {
                    loginViewModel.insertData(this, encyName, encyPassword)
                    updateList()
                }
            }

        }
    }

    private fun updateList() {
        var list = database.loginDao().getLoginDetails() as MutableList<LoginTableModel>
        adapter.setData(list)
    }

    fun encrypt(value: String): String? {
        try {
            val iv = IvParameterSpec(INIT_VECTOR.toByteArray(Charsets.UTF_8))
            val skeySpec = SecretKeySpec(SECRET_KEY.toByteArray(Charsets.UTF_8), "AES")

            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)

            val encrypted = cipher.doFinal(value.toByteArray())
            return Base64.encodeToString(encrypted, Base64.DEFAULT)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }
}