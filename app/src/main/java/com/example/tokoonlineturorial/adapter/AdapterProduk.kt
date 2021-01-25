package com.example.tokoonlineturorial.adapter


import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.activity.DetailProductActivity
import com.example.tokoonlineturorial.activity.LoginActivity

import com.example.tokoonlineturorial.model.Produk
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterProduk(var activity: Activity, var data: ArrayList<Produk>) :
    RecyclerView.Adapter<AdapterProduk.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val layout = view.findViewById<CardView>(R.id.layout_item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_produk, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.tvNama.text = data[position].name
        holder.tvHarga.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(data[position].harga))
        //   holder.imgProduk.setImageResource(data[position].image)

        val image =
            "http://192.168.43.31/tokoonlinetutorial_BackEnd/public/storage/produk/" + data[position].image
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.product)
            .into(holder.imgProduk)

        holder.layout.setOnClickListener {
            val activiti = Intent(activity, DetailProductActivity::class.java)
            val str = Gson().toJson(data[position], Produk::class.java)
            activiti.putExtra("extra", str)

//            activiti.putExtra("name", data[position].name)
//            activiti.putExtra("harga", data[position].harga)
            activity.startActivity(activiti)
        }

    }


}