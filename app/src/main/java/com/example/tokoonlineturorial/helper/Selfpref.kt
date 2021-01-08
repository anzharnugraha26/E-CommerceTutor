package com.example.tokoonlineturorial.helper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class Selfpref(activity: Activity) {
    val login = "login"

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

}