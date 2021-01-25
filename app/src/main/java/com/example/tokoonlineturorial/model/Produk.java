package com.example.tokoonlineturorial.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class Produk implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idTb")
    public int idTb;

    public int id;
    public String name;
    public String harga;
    public String deskripsi;
    public int category_id;
    public String image;
    public String created_at;
    public String updated_at;

    public int jumlah = 1;
    public boolean selected;

}