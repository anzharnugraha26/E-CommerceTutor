package com.example.tokoonlineturorial.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.helper.Helper
import kotlinx.android.synthetic.main.toolbar.*

class TambahAlamatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_alamat)

        Helper().setToolBar(this, toolbar, "Tambah Alamat")
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}