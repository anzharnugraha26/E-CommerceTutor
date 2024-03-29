package com.example.tokoonlineturorial.helper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.tokoonlineturorial.model.User
import com.google.gson.Gson

class Selfpref(activity: Activity) {
    val login = "login"
    val user = "user"
    val nama = "nama"
    val phone = "phone"

    val mypreff = "MAIN_PRF"
    val sp: SharedPreferences

    init {
        sp = activity.getSharedPreferences(mypreff, Context.MODE_PRIVATE)
    }

    fun setStatusLogin(status: Boolean) {
        sp.edit().putBoolean(login, status).apply()
    }

    fun getStatusLogin():Boolean{
        return sp.getBoolean(login, false)
    }

    fun setUser(value: User){
        val data: String = Gson().toJson(value, User::class.java)
        sp.edit().putString(user, data ).apply()
    }

    fun getUser(): User? {
        val data:String = sp.getString(user, null) ?: return null
        return Gson().fromJson<User>(data, User::class.java)
    }

}