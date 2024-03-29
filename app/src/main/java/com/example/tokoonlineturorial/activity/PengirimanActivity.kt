package com.example.tokoonlineturorial.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.adapter.AdapterKurir
import com.example.tokoonlineturorial.app.ApiConfig
import com.example.tokoonlineturorial.app.ApiConfigAlamat
import com.example.tokoonlineturorial.helper.Helper
import com.example.tokoonlineturorial.helper.Selfpref
import com.example.tokoonlineturorial.model.CheckOut
import com.example.tokoonlineturorial.model.ResponModel
import com.example.tokoonlineturorial.model.rajaongkir.Costs
import com.example.tokoonlineturorial.model.rajaongkir.ResponseOngkir
import com.example.tokoonlineturorial.room.MyDatabase
import com.example.tokoonlineturorial.util.Apikey
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_pengiriman.*
import kotlinx.android.synthetic.main.activity_pengiriman.btn_tambahAlamat
import kotlinx.android.synthetic.main.activity_pengiriman.div_kosong
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_tambah_alamat.*
import kotlinx.android.synthetic.main.activity_tambah_alamat.pb
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PengirimanActivity : AppCompatActivity() {
    lateinit var myDb: MyDatabase
    var totalHarga = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengiriman)
        Helper().setToolBar(this, toolbar, "Pengiriman")
        myDb = MyDatabase.getInstance(this)!!
        setSpinner()
        totalHarga = Integer.valueOf(intent.getStringExtra("extra")!!)
        tv_totalBelanja.text = Helper().gantiRupiah(totalHarga)

        mainButton()
    }



    private fun setSpinner() {
        val arryString = ArrayList<String>()
        arryString.add("JNE")
        arryString.add("POS")
        arryString.add("TIKI")
        val adapter = ArrayAdapter<Any>(
            this,
            R.layout.item_spiner,
            arryString.toTypedArray()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_kurir.adapter = adapter

        spn_kurir.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        getOngkir(spn_kurir.selectedItem.toString())
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }
    }

    @SuppressLint("SetTextI18n")
    fun checkAlamat() {

        if (myDb.daoAlamat().geByStatus(true) != null) {
            div_alamat.visibility = View.VISIBLE
            div_kosong.visibility = View.GONE

            div_metodePengiriman.visibility = View.VISIBLE

            val a = myDb.daoAlamat().geByStatus(true)!!
            tv_nama.text = a.name
            tv_phone.text = a.phone
            tv_alamat.text =
                a.alamat + ", " + a.kota + ", " + a.kecamatan + ", " + a.kodepos + ", (" + a.type + ")"
            btn_tambahAlamat.text = "Ubah Alamat"

            getOngkir("JNE")
        } else {
            div_alamat.visibility = View.GONE
            div_kosong.visibility = View.VISIBLE
            btn_tambahAlamat.text = "Tambah Alamat"
        }
    }

    private fun mainButton() {
        btn_tambahAlamat.setOnClickListener {
            startActivity(Intent(this, ListAlamatActivity::class.java))
        }

        btn_bayar_total.setOnClickListener {
            bayar()
        }

    }

    private fun bayar() {
        val a = myDb.daoAlamat().geByStatus(true)!!
        val user = Selfpref(this).getUser()
        val listProduk = myDb.daoKeranjang().getAll() as ArrayList
        var totalItem = 0
        var totalHarga = 0
        val produks = ArrayList<CheckOut.Item>()
        for (p in listProduk) {
            if (p.selected) {
                totalItem += p.jumlah
                totalHarga += (p.jumlah * Integer.valueOf(p.harga))

                val produk = CheckOut.Item()
                produk.id = "" + p.id
                produk.total_item = "" + p.jumlah
                produk.total_harga = "" + (p.jumlah * Integer.valueOf(p.harga))
                produk.catatan = "test"

                produks.add(produk)

            }
        }

        val checkOut = CheckOut()
        checkOut.user_id = "1"
        checkOut.total_item = "" + totalItem
        checkOut.total_harga = "" + totalHarga
        checkOut.name = a.name
        checkOut.phone = a.phone
        checkOut.produks = produks
        checkOut.jasa_pengiriman = jasaKirim
        checkOut.ongkir = ongkir
        checkOut.kurir = kurir
        checkOut.total_transfer = "" + (totalHarga + Integer.valueOf(ongkir))



        val json = Gson().toJson(checkOut, CheckOut::class.java)
        val intent = Intent(this,PembayaranActivity::class.java)
        intent.putExtra("extra", json)
        startActivity(intent)




    }


    private fun getOngkir(kurir: String) {
        val alamat = myDb.daoAlamat().geByStatus(true)
        val origin = "501"
        val destination = "" + alamat!!.idKota
        val berat = 100

        ApiConfigAlamat.instanceRetrofit.ongkir(
            Apikey.key,
            origin,
            destination,
            berat,
            kurir.toLowerCase()
        )
            .enqueue(object : Callback<ResponseOngkir> {
                override fun onFailure(call: Call<ResponseOngkir>, t: Throwable) {
                    Log.d("error", "gagal memuat data" + t.message)
                }

                override fun onResponse(
                    call: Call<ResponseOngkir>,
                    response: Response<ResponseOngkir>
                ) {
                    if (response.isSuccessful) {
                        Log.d("Success", "berhasil memuat data" + response.message())
                        val result = response.body()!!.rajaongkir.results
                        if (result.isNotEmpty()) {
                            displayOngkir(result[0].code.toUpperCase(), result[0].costs)
                        }


                    } else {
                        Log.d("error", "gagal memuat data" + response.message())
                    }

                }
            })
    }

    var ongkir = ""
    var jasaKirim = ""
    var kurir = ""

    private fun displayOngkir(_kurir: String, arrayList: ArrayList<Costs>) {
        var arrayOngkir = ArrayList<Costs>()
        for (i in arrayList.indices) {
            val ongkir = arrayList[i]
            if (i == 0) {
                ongkir.isActive = true
            }
            arrayOngkir.add(ongkir)
        }

        setTotal(arrayOngkir[0].cost[0].value)
        val layaoutManager = LinearLayoutManager(this)
        layaoutManager.orientation = LinearLayoutManager.VERTICAL
        var adapter: AdapterKurir? = null
        adapter = AdapterKurir(arrayOngkir, _kurir, object : AdapterKurir.Listeners {

            override fun onClicked(data: Costs, index: Int) {
                val newArrayOngkir = ArrayList<Costs>()
                for (ongkir in arrayOngkir) {
                    ongkir.isActive = data.description == ongkir.description
                    newArrayOngkir.add(ongkir)
                }
                arrayOngkir = newArrayOngkir
                adapter!!.notifyDataSetChanged()
                setTotal(data.cost[0].value)

                ongkir = data.cost[0].value
                jasaKirim = data.service
                kurir = _kurir

            }

        })

        rv_metode.adapter = adapter
        rv_metode.layoutManager = layaoutManager

    }

    fun setTotal(ongkir: String) {
        tv_biayapengiriman.text = Helper().gantiRupiah(ongkir)
        tv_total_.text = Helper().gantiRupiah(Integer.valueOf(ongkir) + totalHarga)
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