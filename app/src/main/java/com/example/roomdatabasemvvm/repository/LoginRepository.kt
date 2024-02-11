package com.example.room.mvvm.repository

import android.content.Context
import com.example.room.mvvm.room.LoginDatabase
import com.example.roomdatabasemvvm.model.LoginTableModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class LoginRepository {

    companion object {

        var loginDatabase: LoginDatabase? = null

        fun insertData(context: Context, username: String, password: String) {

            loginDatabase = LoginDatabase.getDataseClient(context)

            CoroutineScope(IO).launch {
                val loginDetails = LoginTableModel(username, password)
                loginDatabase!!.loginDao().InsertData(loginDetails)
            }

        }

    }
}