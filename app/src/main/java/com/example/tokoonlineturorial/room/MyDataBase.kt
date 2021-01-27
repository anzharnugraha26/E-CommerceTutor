package com.example.tokoonlineturorial.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tokoonlineturorial.model.Keranjang
import com.example.tokoonlineturorial.model.Produk

@Database(entities = [Produk::class] /* List model Ex:NoteModel */, version = 2)
abstract class MyDatabase : RoomDatabase() {
//    abstract fun daoKeranjang(): DaoKeranjang
//
//    companion object {
//        private var INSTANCE: MyDatabase? = null
//
//        fun getInstance(context: Context): MyDatabase? {
//            if (INSTANCE == null) {
//                synchronized(MyDatabase::class) {
//                    INSTANCE = Room.databaseBuilder(
//                        context.applicationContext,
//                        MyDatabase::class.java, "MyDatabase877" // Database Name
//                    ).allowMainThreadQueries().build()
//                }
//            }
//            return INSTANCE
//        }
//
//        fun destroyInstance() {
//            INSTANCE = null
//        }
//    }
}