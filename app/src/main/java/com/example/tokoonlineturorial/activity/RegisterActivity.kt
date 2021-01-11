package com.example.tokoonlineturorial.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.tokoonlineturorial.MainActivity
import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.app.ApiConfig
import com.example.tokoonlineturorial.helper.Selfpref
import com.example.tokoonlineturorial.model.ResponModel
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    lateinit var s: Selfpref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        s = Selfpref(this)


        btn_regis.setOnClickListener {
            register()
        }

        btn_google.setOnClickListener {
            dataDummy()
        }
    }

    fun dataDummy() {
        nama_lengkap.setText("anzhar Ade Nugrahaa")
        edt_email.setText("anzhar@gmail.com")
        edt_telpon.setText("08989898989889")
        edt_password.setText("123456789")
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

        pb.visibility = View.VISIBLE

        ApiConfig.instanceRetrofit.register(
            nama_lengkap.text.toString(),
            edt_email.text.toString(),
            edt_password.text.toString(),
            edt_telpon.text.toString()
        ).enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                pb.visibility = View.GONE

                val respon = response.body()!!
                if (respon.success == 1) {
                    s.setStatusLogin(true)
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()


                    Toast.makeText(
                        this@RegisterActivity,
                        "Selamat Datang  " + respon.user.name,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Error   " + respon.message,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                pb.visibility = View.GONE
                Toast.makeText(this@RegisterActivity, "error" + t.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })


    }
}