package com.example.tokoonlineturorial.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.adapter.AdapterAlamat
import com.example.tokoonlineturorial.adapter.AdapterProduk
import com.example.tokoonlineturorial.helper.Helper
import com.example.tokoonlineturorial.model.Alamat
import com.example.tokoonlineturorial.model.Produk
import com.example.tokoonlineturorial.room.MyDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list_alamat.*
import kotlinx.android.synthetic.main.toolbar.*

class ListAlamatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_alamat)
        Helper().setToolBar(this, toolbar, "Pilih Alamat")
        mainButton()
    }

    private fun displayAlamat() {
        val myDb = MyDatabase.getInstance(this)!!
        val arrayList = myDb.daoAlamat().getAll() as ArrayList

        if (arrayList.isEmpty()) {
            div_kosong.visibility = View.VISIBLE
        } else {
            div_kosong.visibility = View.GONE
        }

        val layaoutManager = LinearLayoutManager(this)
        layaoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_alamat.adapter = AdapterAlamat( arrayList, object : AdapterAlamat.Listeners{

            override fun onClicked(data: Alamat) {

                if (myDb.daoAlamat().geByStatus(true)!= null){
                    val alamatAktif = myDb.daoAlamat().geByStatus(true)!!
                    alamatAktif.isSelected = false
                    update(alamatAktif)
                }

                data.isSelected = true
                update(data)
               onBackPressed()
            }

        })
        rv_alamat.layoutManager = layaoutManager

    }

    private fun update(data: Alamat) {
        val myDb = MyDatabase.getInstance(this)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoAlamat().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

            })
    }

    override fun onResume() {
        displayAlamat()
        super.onResume()
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