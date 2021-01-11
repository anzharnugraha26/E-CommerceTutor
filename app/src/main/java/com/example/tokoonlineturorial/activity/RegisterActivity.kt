package com.example.tokoonlineturorial.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.app.ApiConfig
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_regis.setOnClickListener {
            register()
        }
    }

    fun register() {
        if (nama_lengkap.text.isEmpty()) {
            nama_lengkap.error = "kolom nama tidak boleh kosong"
            nama_lengkap.requestFocus()
            return
        } else if (edt_email.text.isEmpty()) {
            edt_email.error = "kolom nama tidak boleh kosong"
            edt_email.requestFocus()
            return
        } else if (edt_telpon.text.isEmpty()) {
            edt_telpon.error = "kolom nama tidak boleh kosong"
            edt_telpon.requestFocus()
            return
        } else if (edt_password.text.isEmpty()) {
            edt_password.error = "kolom nama tidak boleh kosong"
            edt_password.requestFocus()
            return
        }

        ApiConfig.instanceRetrofit.register(nama_lengkap.text.toString(), edt_email.text.toString(), edt_password.text.toString()).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
        })


    }
}