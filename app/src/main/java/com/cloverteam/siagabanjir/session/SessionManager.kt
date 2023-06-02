package com.cloverteam.siagabanjir.session
import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefName = "LoginSession"
    private val userId = "userId"

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun createLoginSession(userId: String) {
        editor.putString(this.userId, userId)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.contains(userId)
    }


    fun getUserId(): String? {
        return sharedPreferences.getString(userId, null)
    }

    fun logout() {
        editor.clear()
        editor.apply()
    }
}