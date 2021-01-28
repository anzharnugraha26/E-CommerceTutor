package com.example.tokoonlineturorial.fragment


import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.adapter.AdapterKeranjang
import com.example.tokoonlineturorial.adapter.AdapterProduk
import com.example.tokoonlineturorial.room.MyDatabase

class KeranjangFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        init(view)
        mainButton()

        return view
    }

    private fun displayProduk() {
        val myDb = MyDatabase.getInstance(requireActivity())
        val listProduk = myDb!!.daoKeranjang().getAll() as ArrayList

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_produk.adapter = AdapterKeranjang(requireActivity(), listProduk)
        rv_produk.layoutManager = layoutManager
    }

    private fun mainButton() {
        btn_delete.setOnClickListener {

        }

        btn_delete.setOnClickListener {

        }
    }


    lateinit var btn_delete: ImageView
    lateinit var rv_produk: RecyclerView
    lateinit var tv_total: TextView
    lateinit var btn_bayar: TextView

    private fun init(view: View) {
        btn_bayar = view.findViewById(R.id.btn_bayar)
        tv_total = view.findViewById(R.id.tv_total)
        rv_produk = view.findViewById(R.id.rv_produkkeranjang)
        btn_delete = view.findViewById(R.id.btn_deletekeranjang)
    }

    override fun onResume() {
        displayProduk()
        super.onResume()
    }

}
