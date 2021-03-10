package com.example.tokoonlineturorial.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.activity.PengirimanActivity
import com.example.tokoonlineturorial.adapter.AdapterKeranjang
import com.example.tokoonlineturorial.room.MyDatabase
import com.example.tokoonlineturorial.helper.Helper
import com.example.tokoonlineturorial.model.Produk
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_keranjang.*

class KeranjangFragment : Fragment() {
    lateinit var myDb: MyDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        init(view)
        myDb = MyDatabase.getInstance(requireActivity())!!
        mainButton()

        return view
    }

    lateinit var adapter: AdapterKeranjang
    var listProduk = ArrayList<Produk>()

    private fun displayProduk() {
        listProduk = myDb.daoKeranjang().getAll() as ArrayList

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        adapter =
            AdapterKeranjang(requireActivity(), listProduk, object : AdapterKeranjang.Listeners {
                override fun onUpdate() {
                    hitungTotal()
                }

                override fun onDelete(position: Int) {
                    listProduk.removeAt(position)
                    adapter.notifyDataSetChanged()
                    hitungTotal()
                }

            })

        rv_produk.adapter = adapter

        rv_produk.layoutManager = layoutManager
    }

    var totalHarga = 0

    fun hitungTotal() {
        val listProduk = myDb.daoKeranjang().getAll() as ArrayList
        totalHarga = 0
        var isSelectedAll = true
        for (produk in listProduk) {

            if (produk.selected) {
                val harga = Integer.valueOf(produk.harga)
                totalHarga += (harga * produk.jumlah)
            } else {
                isSelectedAll = false
            }
        }

        ccAll.isChecked = isSelectedAll
        tv_total.text = Helper().gantiRupiah(totalHarga)
    }


    private fun mainButton() {

        btn_bayar.setOnClickListener {
            var isThereProduct = false

            for (p in listProduk) {
                if (p.selected) isThereProduct = true
            }

            if (isThereProduct){
                val intent = Intent(requireActivity(), PengirimanActivity::class.java)
                intent.putExtra("extra", "" + totalHarga)
                startActivity(intent)
            } else{
                Toast.makeText(requireContext(),"tidak ada produk yang dipilih", Toast.LENGTH_SHORT).show()
            }


        }

        btn_delete.setOnClickListener {
            val listDelete = ArrayList<Produk>()
            for (p in listProduk) {
                if (p.selected) listDelete.add(p)
            }

            delete(listDelete)
        }

        ccAll.setOnClickListener {
            for (i in listProduk.indices) {
                val produk = listProduk[i]
                produk.selected = ccAll.isChecked
                listProduk[i] = produk
            }
            adapter.notifyDataSetChanged()
        }
    }


    lateinit var btn_delete: ImageView
    lateinit var rv_produk: RecyclerView
    lateinit var tv_total: TextView
    lateinit var btn_bayar: TextView
    lateinit var ccAll: CheckBox


    private fun delete(data: ArrayList<Produk>) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().delete(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listProduk.clear()
                listProduk.addAll(myDb.daoKeranjang().getAll() as ArrayList)
                adapter.notifyDataSetChanged()
            })
    }


    private fun init(view: View) {
        btn_bayar = view.findViewById(R.id.btn_bayar)
        tv_total = view.findViewById(R.id.tv_total)
        rv_produk = view.findViewById(R.id.rv_produkkeranjang)
        btn_delete = view.findViewById(R.id.btn_deletekeranjang)
        ccAll = view.findViewById(R.id.cc_all)
    }

    override fun onResume() {
        displayProduk()
        hitungTotal()
        super.onResume()
    }

}
