package com.example.tokoonlineturorial.adapter

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.activity.DetailProductActivity

import com.example.tokoonlineturorial.model.Produk
import com.example.tokoonlineturorial.room.MyDatabase
import com.example.tokoonlineturorial.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterKeranjang(
    var activity: Activity,
    var data: ArrayList<Produk>
) : RecyclerView.Adapter<AdapterKeranjang.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val layout = view.findViewById<CardView>(R.id.layout_keranjang)
        val btnTambah = view.findViewById<ImageView>(R.id.btn_tambah)
        val btnKurang = view.findViewById<ImageView>(R.id.btn_kurang)
        val btnDelete = view.findViewById<ImageView>(R.id.btn_delete_item)
        val check = view.findViewById<CheckBox>(R.id.tvcheckbox)

        val tvjumlah = view.findViewById<TextView>(R.id.tv_jumlah)

    }


    interface Listeners {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_keranjang, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val produk = data[position]


        holder.tvNama.text = produk.name
        holder.tvHarga.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(produk.harga))

        var jumlah = data[position].jumlah
        holder.tvjumlah.text = jumlah.toString()

        val image =
            Config.productUrl + data[position].image
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.product)
            .into(holder.imgProduk)

        holder.btnTambah.setOnClickListener {
            jumlah++
            produk.jumlah = jumlah
            update(produk)
            holder.tvjumlah.text = jumlah.toString()
        }

        holder.btnKurang.setOnClickListener {
            if (jumlah <= 1) return@setOnClickListener
            jumlah--

            produk.jumlah = jumlah
            update(produk)
            holder.tvjumlah.text = jumlah.toString()
        }

        holder.btnDelete.setOnClickListener {
            notifyItemRemoved(position)
            delete(produk)
            
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


    private fun update(data: Produk) {
        val myDb = MyDatabase.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoKeranjang().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

            })
    }

    private fun delete(data: Produk) {
        val myDb = MyDatabase.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoKeranjang().delete(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

            })
    }


}