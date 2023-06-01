package com.cloverteam.siagabanjir.db

import com.cloverteam.siagabanjir.model.User
import org.w3c.dom.Text

interface DatabaseIndicator {
    // -- Menambahkan user baru ke dalam database --
    fun addUser(user: User): Long

    // -- Mendapatkan user berdasarkan username --
    fun getEmail(email: String): Text?

    // -- Mengupdate data user di dalam database --
    fun updateUser(user: User): Int

    // -- Menghapus user dari database --
    fun deleteUser(user: User): Int

    // -- Mendapatkan semua user dari database --
    fun getAllUsers(): List<User>
}