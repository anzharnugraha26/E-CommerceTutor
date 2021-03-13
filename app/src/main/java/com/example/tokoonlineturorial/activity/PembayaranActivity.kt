package com.example.tokoonlineturorial.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.app.ApiConfig
import com.example.tokoonlineturorial.model.CheckOut
import com.example.tokoonlineturorial.model.ResponModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_pembayaran.*
import kotlinx.android.synthetic.main.activity_tambah_alamat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PembayaranActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)



        btn_bca.setOnClickListener {
            bayar("bca")
        }
        btn_bri.setOnClickListener {
            bayar("bri")
        }
        btn_mandiri.setOnClickListener {
            bayar("mandiri")
        }
    }

    fun bayar(bank: String) {
        val json = intent.getStringExtra("extra")!!.toString()
        val checkout = Gson().fromJson(json, CheckOut::class.java)
        checkout.bank = bank
        ApiConfig.instanceRetrofit.checkout(
            checkout
        ).enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val respon = response.body()!!
                if (respon.success == 1) {

                    Toast.makeText(
                        this@PembayaranActivity,
                        "Success",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(
                        this@PembayaranActivity,
                        "Error   " + respon.message,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                pb.visibility = View.GONE
                Toast.makeText(this@PembayaranActivity, "error" + t.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }


}