package com.org.proyektingkat2.session
import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefName = "LoginSession"
    private val keyEmail = "email"

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun createLoginSession(email: String) {
        editor.putString(keyEmail, email)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.contains(keyEmail)
    }


    fun getEmail(): String? {
        return sharedPreferences.getString(keyEmail, null)
    }

    fun logout() {
        editor.clear()
        editor.apply()
    }
}