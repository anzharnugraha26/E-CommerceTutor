package com.example.tokoonlineturorial.adapter


import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView

import androidx.recyclerview.widget.RecyclerView
import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.activity.DetailProductActivity
import com.example.tokoonlineturorial.activity.LoginActivity
import com.example.tokoonlineturorial.helper.Helper
import com.example.tokoonlineturorial.model.Alamat

import com.example.tokoonlineturorial.model.Produk
import com.example.tokoonlineturorial.model.rajaongkir.Costs
import com.example.tokoonlineturorial.model.rajaongkir.Result
import com.example.tokoonlineturorial.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterKurir(var data: ArrayList<Costs>, var kurir: String, var listener: Listeners) :
    RecyclerView.Adapter<AdapterKurir.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvLamaPengiriman = view.findViewById<TextView>(R.id.tv_lamapengiriman)
        val tvBerat = view.findViewById<TextView>(R.id.tv_berat)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val rd = view.findViewById<RadioButton>(R.id.rd)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_kurir, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val a = data[position]
        holder.tvNama.text = kurir + " " + a.service

        val cost = a.cost[0]
        holder.tvLamaPengiriman.text = cost.etd + " hari kerja"
        holder.tvHarga.text = Helper().gantiRupiah(cost.value)
        holder.tvBerat.text = "1 kg " + Helper().gantiRupiah(cost.value)

    }


    interface Listeners {
        fun onClicked(data: Alamat)
    }

}