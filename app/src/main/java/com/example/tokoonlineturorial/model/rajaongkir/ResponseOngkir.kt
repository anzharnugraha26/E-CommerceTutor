package com.example.tokoonlineturorial.model.rajaongkir

import com.example.tokoonlineturorial.model.ModelAlamat

class ResponseOngkir {
    val rajaongkir = RajaOngkir()

    class RajaOngkir {
        val results = ArrayList<Result>()
    }

}