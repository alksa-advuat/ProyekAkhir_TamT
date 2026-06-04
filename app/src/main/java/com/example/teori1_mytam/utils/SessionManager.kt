package com.example.teori1_mytam.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME  = "mealkost_session"
        private const val KEY_TOKEN  = "auth_token"
        private const val KEY_EMAIL  = "user_email"
        private const val KEY_NAME   = "user_name"
        private const val KEY_LOGGED = "is_logged_in"
        private const val KEY_BUDGET = "user_budget"
        private const val KEY_SISA_BUDGET = "sisa_budget"
        private const val KEY_BUDGET_DATE = "budget_date"
        private const val KEY_USER_ID     = "user_id"
    }

    fun saveSession(token: String, email: String, name: String, userId: String) {
        prefs.edit()
            .putString(KEY_TOKEN,   token)
            .putString(KEY_EMAIL,   email)
            .putString(KEY_NAME,    name)
            .putString(KEY_USER_ID, userId)
            .putBoolean(KEY_LOGGED, true)
            .apply()
    }

    fun getUserId(): String = prefs.getString(KEY_USER_ID, "") ?: ""

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_LOGGED, false)
    fun getToken(): String?   = prefs.getString(KEY_TOKEN, null)
    fun getEmail(): String?   = prefs.getString(KEY_EMAIL, null)
    fun getName(): String?    = prefs.getString(KEY_NAME,  null)

    fun saveBudget(budget: Int) {
        prefs.edit().putInt(KEY_BUDGET, budget).apply()
    }
    fun saveSisaBudget(sisa: Int) {
        prefs.edit().putInt(KEY_SISA_BUDGET, sisa).apply()
    }

    fun getSisaBudget(): Int = prefs.getInt(KEY_SISA_BUDGET, -1) // -1 = belum ada

    fun saveBudgetDate(date: String) {
        prefs.edit().putString(KEY_BUDGET_DATE, date).apply()
    }

    fun getBudgetDate(): String = prefs.getString(KEY_BUDGET_DATE, "") ?: ""

    fun getBudget(): Int = prefs.getInt(KEY_BUDGET, 25000)

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}