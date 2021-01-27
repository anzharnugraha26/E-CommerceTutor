package com.example.tokoonlineturorial.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.helper.Helper
import com.example.tokoonlineturorial.model.Keranjang
import com.example.tokoonlineturorial.model.Produk
import com.example.tokoonlineturorial.room.MyDatabase
import com.example.tokoonlineturorial.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_product.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailProductActivity : AppCompatActivity() {
    lateinit var produk: Produk
    lateinit var myDb: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
        myDb = MyDatabase.getInstance(this)!!
        getInfo()
        mainButton()
    }

    fun mainButton() {
        btn_keranjang.setOnClickListener {
            insert()
        }

        btn_favorit.setOnClickListener {
            val myDb: MyDatabase = MyDatabase.getInstance(this)!! // call database
            val listNote = myDb.daoKeranjang().getAll() // get All data
            for (note: Produk in listNote) {
                println("-----------------------")
                println(note.name)
                println(note.harga)
            }
        }
    }

    fun insert() {
        val my_db: MyDatabase = MyDatabase.getInstance(this)!!
        CompositeDisposable().add(Observable.fromCallable { my_db.daoKeranjang().insert(produk) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Toast.makeText(this, "Berhasil menambah kekeranjang", Toast.LENGTH_SHORT).show()
                Log.d("respons", "data inserted")
            })
    }



    fun getInfo() {
        val data = intent.getStringExtra("extra")
        produk = Gson().fromJson<Produk>(data, Produk::class.java)

        tv_nama.text = produk.name
        tv_harga.text = Helper().gantiRupiah(produk.harga)
        tv_deskripsi.text = produk.deskripsi

        val img =
            Config.productUrl + produk.image
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.product)
            .resize(400, 400)
            .into(image)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = produk.name
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}