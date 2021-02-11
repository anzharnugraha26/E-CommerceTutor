package com.example.tokoonlineturorial.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.tokoonlineturorial.model.Alamat
import com.example.tokoonlineturorial.model.Keranjang
import com.example.tokoonlineturorial.model.Produk

@Dao
interface DaoAlamat {
    @Insert(onConflict = REPLACE)
    fun insert(data: Alamat)

    @Delete
    fun delete(data: Alamat)

    @Update
    fun update(data: Alamat): Int

    @Query("SELECT * from alamat ORDER BY id ASC")
    fun getAll(): List<Alamat>

    @Query("SELECT * FROM alamat WHERE id = :id LIMIT 1")
    fun getId(id: Int): Alamat

    @Query("DELETE FROM alamat")
    fun deleteAll(): Int
}