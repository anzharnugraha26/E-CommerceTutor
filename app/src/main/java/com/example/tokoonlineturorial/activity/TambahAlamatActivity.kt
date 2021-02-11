package com.example.tokoonlineturorial.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.app.ApiConfig
import com.example.tokoonlineturorial.app.ApiConfigAlamat
import com.example.tokoonlineturorial.helper.Helper
import com.example.tokoonlineturorial.model.Alamat
import com.example.tokoonlineturorial.model.ModelAlamat
import com.example.tokoonlineturorial.model.ResponModel
import com.example.tokoonlineturorial.room.MyDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_tambah_alamat.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahAlamatActivity : AppCompatActivity() {

    var provinsi = ModelAlamat()
    var kota = ModelAlamat()
    var kecamatan = ModelAlamat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_alamat)
        Helper().setToolBar(this, toolbar, "Tambah Alamat")
        mainButton()
        getProvinsi()
    }

    private fun mainButton() {
        btn_simpan.setOnClickListener {
            simpan()
        }
    }

    private fun simpan() {
        when {
            edt_nama.text.isEmpty() -> {
                edt_nama.error = "kolom nama tidak boleh kosong"
                edt_nama.requestFocus()
                return
            }
            edt_type.text.isEmpty() -> {
                edt_type.error = "kolom type tidak boleh kosong"
                edt_type.requestFocus()
                return
            }
            edt_phone.text.isEmpty() -> {
                edt_phone.error = "kolom telepon tidak boleh kosong"
                edt_phone.requestFocus()
                return
            }
            edt_alamat.text.isEmpty() -> {
                edt_alamat.error = "kolom alamat tidak boleh kosong"
                edt_alamat.requestFocus()
                return
            }
            edt_kodePos.text.isEmpty() -> {
                edt_kodePos.error = "kolom type tidak boleh kosong"
                edt_kodePos.requestFocus()
                return
            }

        }

        if (provinsi.id == 0) {
            toast("Silahkan pilih provinsi")
            return
        }

        if (kota.id == 0) {
            toast("Silahkan pilih Kota")
            return
        }

        if (kecamatan.id == 0) {
            toast("Silahkan pilih Kecamatan")
            return
        }

        val alamat = Alamat()
        alamat.name = edt_nama.text.toString()
        alamat.type = edt_type.text.toString()
        alamat.phone = edt_phone.text.toString()
        alamat.alamat = edt_alamat.text.toString()
        alamat.kodepos = edt_kodePos.text.toString()

        alamat.idProvinsi = provinsi.id
        alamat.provinsi = provinsi.nama
        alamat.idKecamatan = kecamatan.id
        alamat.kecamatan = kecamatan.nama
        alamat.idKota = kota.id
        alamat.kota = kota.nama

        insert(alamat)
    }


    fun toast(string: String) {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
    }

    private fun insert(data: Alamat) {
        val myDb = MyDatabase.getInstance(this)!!
        CompositeDisposable().add(Observable.fromCallable { myDb.daoAlamat().insert(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                toast("data berhasil di simpan")
                onBackPressed()
//                for (alamat in myDb.daoAlamat().getAll()){
//                    Log.d("alamat", "nama:" + alamat.provinsi )
//                }
            })
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

                    val listProvinsi = res.provinsi
                    val arryString = ArrayList<String>()
                    arryString.add("Pilih Provinsi")

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
                                    provinsi = listProvinsi[position - 1]
                                    val idProv = listProvinsi[position - 1].id
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
        pb.visibility = View.VISIBLE
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
                    for (data in listArray) {
                        arryString.add(data.nama)
                    }

                    val adapter = ArrayAdapter<Any>(
                        this@TambahAlamatActivity,
                        R.layout.item_spiner,
                        arryString.toTypedArray()
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_kota.adapter = adapter

                    spn_kota.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                if (position != 0) {
                                    kota = listArray[position - 1]
                                    val idKota = listArray[position - 1].id
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
        pb.visibility = View.VISIBLE
        ApiConfigAlamat.instanceRetrofit.getKecamatan(id)
            .enqueue(object : Callback<ResponModel> {
                override fun onFailure(call: Call<ResponModel>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<ResponModel>,
                    response: Response<ResponModel>
                ) {
                    if (response.isSuccessful) {

                        pb.visibility = View.GONE
                        div_kecamatan.visibility = View.VISIBLE
                        val res = response.body()!!
                        val listArray = res.kecamatan


                        val arryString = ArrayList<String>()
                        arryString.add("Pilih Kecamatan")
                        for (data in res.kecamatan) {
                            arryString.add(data.nama)
                        }

                        val adapter = ArrayAdapter<Any>(
                            this@TambahAlamatActivity,
                            R.layout.item_spiner,
                            arryString.toTypedArray()
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spn_kecamatan.adapter = adapter
                        spn_kecamatan.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    if (position != 0) {
                                        kecamatan = listArray[position - 1]
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


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}