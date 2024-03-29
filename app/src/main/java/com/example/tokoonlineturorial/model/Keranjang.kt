package com.example.tokoonlineturorial.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "keranjang") // the name of tabel
class Keranjang {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null

    lateinit var id_produk: String
    lateinit var name: String
    var harga: Int = 0
    lateinit var image: String
    var jumlah: Int = 0
}