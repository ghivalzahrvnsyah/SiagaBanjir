package com.cloverteam.siagabanjir.model

data class Report(
    var id: String = "",
    val description: String = "",
    val date: String = "",
    val area: String = "",
    val status: Int? = null,
    var userId: String = ""
)
