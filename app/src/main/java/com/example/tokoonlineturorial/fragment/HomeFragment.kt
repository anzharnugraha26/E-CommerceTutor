package com.example.tokoonlineturorial.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.adapter.AdapterProduk
import com.example.tokoonlineturorial.adapter.AdapterSlider
import com.example.tokoonlineturorial.model.Produk


class HomeFragment : Fragment() {
    lateinit var vpSlider: ViewPager
    lateinit var rvProduk: RecyclerView
    lateinit var rvProdukTerlaris: RecyclerView
    lateinit var rvElektronik: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        vpSlider = view.findViewById(R.id.vp_slider)
        rvProduk = view.findViewById(R.id.rv_product)
        rvProdukTerlaris = view.findViewById(R.id.rv_productTerlaris)
        rvElektronik = view.findViewById(R.id.rv_elektronik)


        val arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.slider1)
        arrSlider.add(R.drawable.slider2)
        arrSlider.add(R.drawable.slider3)

        val adapterSlider = AdapterSlider(arrSlider, activity)
        vpSlider.adapter = adapterSlider

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManager2 = LinearLayoutManager(activity)
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManager3 = LinearLayoutManager(activity)
        layoutManager3.orientation = LinearLayoutManager.HORIZONTAL


        rvProduk.adapter = AdapterProduk(arrProduk)
        rvProduk.layoutManager = layoutManager

        rvProdukTerlaris.adapter = AdapterProduk(arrProdukTerlaris)
        rvProdukTerlaris.layoutManager = layoutManager2

        rvElektronik.adapter = AdapterProduk(arrProdukElekro)
        rvElektronik.layoutManager = layoutManager3


        return view
    }


    val arrProduk: ArrayList<Produk>
        get() {
            val arr = ArrayList<Produk>()
            val p1 = Produk()
            p1.nama = "Hp Core A3"
            p1.harga = "Rp. 5.000.000"
            p1.gambar = R.drawable.hp_14_bs749tu


            val p2 = Produk()
            p2.nama = "Hp Core A3zsa"
            p2.harga = "Rp. 5.000.000"
            p2.gambar = R.drawable.hp_envy_13_aq0019tx

            val p3 = Produk()
            p3.nama = "Hp Core Asaasasas3"
            p3.harga = "Rp. 5.000.000"
            p3.gambar = R.drawable.hp_notebook_14_bs709tu

            arr.add(p1)
            arr.add(p2)
            arr.add(p3)

            return arr
        }

    val arrProdukTerlaris: ArrayList<Produk>
        get() {
            val arr = ArrayList<Produk>()
            val p1 = Produk()
            p1.nama = "HP 14_bs749tu"
            p1.harga = "Rp.5.500.000"
            p1.gambar = R.drawable.hp_14_bs749tu

            val p2 = Produk()
            p2.nama = "Hp Envy_13_aq0019tx"
            p2.harga = "Rp.15.980.000"
            p2.gambar = R.drawable.hp_pavilion_15_cx0056wm

            val p3 = Produk()
            p3.nama = "HP pavilion_13_an0006na"
            p3.harga = "Rp.14.200.000"
            p3.gambar = R.drawable.hp_pavilion_14_ce1507sa

            val p4 = Produk()
            p4.nama = "Hp Envy_13_aq0019tx"
            p4.harga = "Rp.15.980.000"
            p4.gambar = R.drawable.hp_pavilion_14_ce1507sa

            arr.add(p4)
            arr.add(p1)
            arr.add(p3)
            arr.add(p2)

            return arr
        }


    val arrProdukElekro: ArrayList<Produk>
        get() {
            val arr = ArrayList<Produk>()
            val p1 = Produk()
            p1.nama = "HP 14_bs749tu"
            p1.harga = "Rp.5.500.000"
            p1.gambar = R.drawable.hp_14_bs749tu

            val p2 = Produk()
            p2.nama = "Hp Envy_13_aq0019tx"
            p2.harga = "Rp.15.980.000"
            p2.gambar = R.drawable.hp_pavilion_15_cx0056wm

            val p3 = Produk()
            p3.nama = "HP pavilion_13_an0006na"
            p3.harga = "Rp.14.200.000"
            p3.gambar = R.drawable.hp_pavilion_14_ce1507sa

            val p4 = Produk()
            p4.nama = "Hp Envy_13_aq0019tx"
            p4.harga = "Rp.15.980.000"
            p4.gambar = R.drawable.hp_pavilion_14_ce1507sa

            arr.add(p4)
            arr.add(p1)
            arr.add(p3)
            arr.add(p2)

            return arr
        }


}