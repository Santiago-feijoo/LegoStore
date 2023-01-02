package com.feijoo.legostore.common

import android.content.Context
import android.content.SharedPreferences
import com.feijoo.legostore.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Preferences @Inject constructor(@ApplicationContext context: Context) {
    /** Attributes **/
    private val preferences: SharedPreferences = context.getSharedPreferences("data${context.getString(R.string.app_name)}", Context.MODE_PRIVATE)

    /** Methods **/
    fun setBoolean(key: String, value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()

    }

    fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)

    }

    fun setString(key: String, value: String) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()

    }

    fun getString(key: String): String {
        return preferences.getString(key, "").toString()

    }

}