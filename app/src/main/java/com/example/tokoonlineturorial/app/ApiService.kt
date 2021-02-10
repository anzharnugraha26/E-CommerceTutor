package com.example.tokoonlineturorial.app

import com.example.tokoonlineturorial.model.ResponModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("regis")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("phone") phone: String
    ): Call<ResponModel>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponModel>


    @GET("produk")
    fun produk(): Call<ResponModel>

    @GET("provinsi")
    fun getProvinsi(): Call<ResponModel>


    @GET("kota")
    fun getKota(
        @Query("id_provinsi") id: Int
    ): Call<ResponModel>


    @GET("kecamatan")
    fun getKecamatan(
        @Query("id_kota") id: Int
    ): Call<ResponModel>

}