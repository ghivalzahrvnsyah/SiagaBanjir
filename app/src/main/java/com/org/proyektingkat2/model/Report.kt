package com.org.proyektingkat2.model

data class Report(
    val id: Int,
    val description: String,
    val date: String,
    val area: String,
    val status: Int,
    val userEmail: String
)
