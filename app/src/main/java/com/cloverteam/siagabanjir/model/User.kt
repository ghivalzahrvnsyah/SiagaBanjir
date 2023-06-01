package com.cloverteam.siagabanjir.model

data class User(
    val id: Int,
    var email: String,
    var namaLengkap: String,
    var noTelepon: String,
    var alamat: String,
    var password: String,
)