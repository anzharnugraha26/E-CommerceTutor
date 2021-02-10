package com.example.tokoonlineturorial.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.helper.Helper
import kotlinx.android.synthetic.main.activity_list_alamat.*
import kotlinx.android.synthetic.main.toolbar.*

class ListAlamatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_alamat)
        Helper().setToolBar(this, toolbar, "Pilih Alamat")
        mainButton()
    }

    private fun mainButton() {
        btn_tambahAlamat.setOnClickListener {
            startActivity(Intent(this, TambahAlamatActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}