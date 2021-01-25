package com.example.tokoonlineturorial.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.tokoonlineturorial.model.Keranjang

@Dao
interface DaoKeranjang {
    @Insert(onConflict = REPLACE)
    fun insert(data: Keranjang)

    @Delete
    fun delete(data: Keranjang)

    @Update
    fun update(data: Keranjang): Int

    @Query("SELECT * from keranjang ORDER BY id ASC")
    fun getAll(): List<Keranjang>

    @Query("SELECT * FROM keranjang WHERE id = :id LIMIT 1")
    fun getId(id: Int): Keranjang

    @Query("DELETE FROM keranjang")
    fun deleteAll(): Int
}