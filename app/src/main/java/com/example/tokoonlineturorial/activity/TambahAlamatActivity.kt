package com.example.tokoonlineturorial.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.app.ApiConfig
import com.example.tokoonlineturorial.app.ApiConfigAlamat
import com.example.tokoonlineturorial.helper.Helper
import com.example.tokoonlineturorial.model.ResponModel
import kotlinx.android.synthetic.main.activity_tambah_alamat.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahAlamatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_alamat)
        Helper().setToolBar(this, toolbar, "Tambah Alamat")

        getProvinsi()
    }

    private fun getProvinsi() {
        ApiConfigAlamat.instanceRetrofit.getProvinsi().enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                if (response.isSuccessful) {

                    pb.visibility = View.GONE
                    div_provinsi.visibility = View.VISIBLE
                    val res = response.body()!!
                    val arryString = ArrayList<String>()
                    arryString.add("Pilih Provinsi")
                    val listProvinsi = res.provinsi
                    for (prov in listProvinsi) {
                        arryString.add(prov.nama)
                    }

                    val adapter = ArrayAdapter<Any>(
                        this@TambahAlamatActivity,
                        R.layout.item_spiner,
                        arryString.toTypedArray()
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_provinsi.adapter = adapter
                    spn_provinsi.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                if (position != 0) {
                                    val idProv = listProvinsi[position - 1].id
                                    Log.d(
                                        "response",
                                        "provinsi_id:" + idProv + "-" + " name:" + listProvinsi[position - 1].nama
                                    )
                                    getKota(idProv)
                                }
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }

                        }

                } else {
                    Log.d("error", "gagal memuat data" + response.message())
                }

            }
        })
    }


    private fun getKota(id: Int) {
        ApiConfigAlamat.instanceRetrofit.getKota(id).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                if (response.isSuccessful) {

                    pb.visibility = View.GONE
                    div_kota.visibility = View.VISIBLE
                    val res = response.body()!!
                    val listArray = res.kota_kabupaten
                    val arryString = ArrayList<String>()
                    arryString.add("Pilih Kota")
                    for (prov in listArray) {
                        arryString.add(prov.nama)
                    }

                    val adapter = ArrayAdapter<Any>(
                        this@TambahAlamatActivity,
                        R.layout.item_spiner,
                        arryString.toTypedArray()
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_kota.adapter = adapter

                    spn_kota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            if (position != 0) {
                                val idKota = listArray[position - 1].id
                                Log.d(
                                    "response",
                                    "provinsi_id:" + idKota + "-" + " name:" + listArray[position - 1].nama
                                )
                                getKecamatan(idKota)
                            }

                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                    }
                } else {
                    Log.d("error", "gagal memuat data" + response.message())
                }

            }
        })
    }

    private fun getKecamatan(id: Int) {
        ApiConfigAlamat.instanceRetrofit.getKecamatan(id).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                if (response.isSuccessful) {

                    pb.visibility = View.GONE
                    div_kecamatan.visibility = View.VISIBLE
                    val res = response.body()!!
                    val arryString = ArrayList<String>()
                    arryString.add("Pilih Kecamatan")
                    for (prov in res.kecamatan) {
                        arryString.add(prov.nama)
                    }

                    val adapter = ArrayAdapter<Any>(
                        this@TambahAlamatActivity,
                        R.layout.item_spiner,
                        arryString.toTypedArray()
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_kecamatan.adapter = adapter
                } else {
                    Log.d("error", "gagal memuat data" + response.message())
                }

            }
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}