package com.example.tokoonlineturorial.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.helper.Helper
import com.example.tokoonlineturorial.model.Produk
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_product.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
        getInfo()
    }

    fun getInfo() {
        val data = intent.getStringExtra("extra")
        val produk = Gson().fromJson<Produk>(data, Produk::class.java)

        tv_nama.text = produk.name
        tv_harga.text = Helper().gantiRupiah(produk.harga)
        tv_deskripsi.text = produk.deskripsi

        val img =
            "http://192.168.43.31/tokoonlinetutorial_BackEnd/public/storage/produk/" + produk.image
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.product)
            .resize(400,400)
            .into(image)

        setSupportActionBar(toolbar)
        supportActionBar!!.title  = produk.name
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}