package com.org.proyektingkat2.session

import android.content.Context
import android.content.SharedPreferences
import com.org.proyektingkat2.model.User

class SessionManager(context: Context) {
    private val pref: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val PREF_NAME = "Session"
    private val KEY_IS_LOGGED_IN = "isLoggedIn"
    private val KEY_USER_ID = "userId"

    init {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = pref.edit()
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setUserId(userId: Int) {
        editor.putInt(KEY_USER_ID, userId)
        editor.apply()
    }

    fun getUserId(): Int {
        return pref.getInt(KEY_USER_ID, -1)
    }

    fun logout() {
        editor.clear()
        editor.apply()
    }
}