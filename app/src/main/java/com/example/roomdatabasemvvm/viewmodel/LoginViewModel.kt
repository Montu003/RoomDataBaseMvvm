package com.example.room.mvvm.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.room.mvvm.repository.LoginRepository

class LoginViewModel : ViewModel() {

    fun insertData(context: Context, username: String, password: String) {
        LoginRepository.insertData(context, username, password)
    }
}