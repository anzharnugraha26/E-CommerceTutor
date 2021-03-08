package com.example.tokoonlineturorial.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.adapter.AdapterKurir
import com.example.tokoonlineturorial.app.ApiConfigAlamat
import com.example.tokoonlineturorial.helper.Helper
import com.example.tokoonlineturorial.model.Alamat
import com.example.tokoonlineturorial.model.rajaongkir.Costs
import com.example.tokoonlineturorial.model.rajaongkir.ResponseOngkir
import com.example.tokoonlineturorial.room.MyDatabase
import com.example.tokoonlineturorial.util.Apikey
import kotlinx.android.synthetic.main.activity_pengiriman.*
import kotlinx.android.synthetic.main.activity_pengiriman.btn_tambahAlamat
import kotlinx.android.synthetic.main.activity_pengiriman.div_kosong
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PengirimanActivity : AppCompatActivity() {
    lateinit var  myDb : MyDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengiriman)
        Helper().setToolBar(this, toolbar, "Pengiriman")
        myDb = MyDatabase.getInstance(this)!!


        mainButton()
    }

    @SuppressLint("SetTextI18n")
    fun checkAlamat() {

        if (myDb.daoAlamat().geByStatus(true) != null) {
            div_alamat.visibility = View.VISIBLE
            div_kosong.visibility = View.GONE
            tv_kosong.visibility = View.GONE

            div_metodePengiriman.visibility = View.VISIBLE

            val a = myDb.daoAlamat().geByStatus(true)!!
            tv_nama.text = a.name
            tv_phone.text = a.phone
            tv_alamat.text =
                a.alamat + ", " + a.kota + ", " + a.kecamatan + ", " + a.kodepos + ", (" + a.type + ")"
            btn_tambahAlamat.text = "Ubah Alamat"

            getOngkir()
        } else {
            div_alamat.visibility = View.GONE
            div_kosong.visibility = View.VISIBLE
            btn_tambahAlamat.text = "Tambah Alamat"
            tv_kosong.visibility = View.GONE
        }
    }

    private fun mainButton() {
        btn_tambahAlamat.setOnClickListener {
            startActivity(Intent(this, ListAlamatActivity::class.java))
        }
    }

    private fun getOngkir() {
        val alamat = myDb.daoAlamat().geByStatus(true)
        val origin = "501"
        val destination = "" + alamat!!.idKota
        val berat = 100
        val kurir = "jne"

        ApiConfigAlamat.instanceRetrofit.ongkir(Apikey.key, origin, destination , berat, kurir)
            .enqueue(object : Callback<ResponseOngkir> {
                override fun onFailure(call: Call<ResponseOngkir>, t: Throwable) {
                    Log.d("error", "gagal memuat data" + t.message)
                }

                override fun onResponse(call: Call<ResponseOngkir>, response: Response<ResponseOngkir>) {
                    if (response.isSuccessful) {
                        Log.d("Success", "berhasil memuat data" + response.message())
                        val result = response.body()!!.rajaongkir.results
                        if (result.isNotEmpty()){
                            displayOngkir( result[0].code.toUpperCase() ,  result[0].costs)
                        }


                    } else {
                        Log.d("error", "gagal memuat data" + response.message())
                    }

                }
            })
    }
    private fun displayOngkir(kurir: String , arrayList: ArrayList<Costs>) {
        val layaoutManager = LinearLayoutManager(this)
        layaoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_metode.adapter = AdapterKurir( arrayList, kurir ,object : AdapterKurir.Listeners{

            override fun onClicked(data: Alamat) {

            }

        })
        rv_metode.layoutManager = layaoutManager

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        checkAlamat()

        super.onResume()
    }
}