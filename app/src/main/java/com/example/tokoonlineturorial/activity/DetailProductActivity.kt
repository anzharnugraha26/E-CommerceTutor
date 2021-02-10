package com.example.tokoonlineturorial.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
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
import kotlinx.android.synthetic.main.toolbar.toolbar
import kotlinx.android.synthetic.main.toolbar_custom.*

class DetailProductActivity : AppCompatActivity() {
    lateinit var produk: Produk
    lateinit var myDb: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
        myDb = MyDatabase.getInstance(this)!!
        getInfo()
        mainButton()
        checkKeranjang()
    }

    private fun mainButton() {
        beli_sekarang.setOnClickListener {
            Toast.makeText(this, "Berhasil menambah kekeranjang", Toast.LENGTH_SHORT).show()
        }

        btn_keranjangaaaaa.setOnClickListener {
            val data = myDb.daoKeranjang().getId(produk.id)

            if (data == null) {
                insert()
            } else {
                data.jumlah += 1
                update(data)
            }


        }

        btn_keranjang.setOnClickListener {
            val intent = Intent("event:keranjang")
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            onBackPressed()
        }

        btn_favorit.setOnClickListener {
            Toast.makeText(this, "Berhasil menambah kekeranjang", Toast.LENGTH_SHORT).show()
            val listNote = myDb.daoKeranjang().getAll() // get All data
            for (note: Produk in listNote) {
                println("-----------------------")
                println(note.name)
                println(note.harga)
            }
        }
    }


    private fun update(data: Produk) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Toast.makeText(this, "Berhasil menambah kekeranjang", Toast.LENGTH_SHORT).show()
                Log.d("respons", "data inserted")
            })
    }

    private fun insert() {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().insert(produk) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Toast.makeText(this, "Berhasil menambah kekeranjang", Toast.LENGTH_SHORT).show()
                Log.d("respons", "data inserted")
            })
    }


    private fun checkKeranjang() {
        val keranjang = myDb.daoKeranjang().getAll()

        if (keranjang.isNotEmpty()) {
            div_angka.visibility = View.VISIBLE
            tv_angka.text = keranjang.size.toString()
        } else {
            div_angka.visibility = View.GONE
        }
    }


    private fun getInfo() {
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


        Helper().setToolBar(this, toolbar, produk.name)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}