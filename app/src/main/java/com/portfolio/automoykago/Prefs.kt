package com.portfolio.automoykago

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

object Prefs {
    private const val NAME = "automoykago_prefs"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_PASSWORD = "user_password"
    private const val KEY_IS_REGISTERED = "is_registered"
    private const val KEY_SELECTED_ADDRESS_INDEX = "selected_address_index"
    private const val KEY_DARK_THEME = "dark_theme"
    private const val KEY_CARD_LINKED = "card_linked"
    private const val KEY_CARD_LAST4 = "card_last4"
    private const val KEY_BALANCE = "balance"

    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    fun isRegistered(context: Context): Boolean =
        prefs(context).getBoolean(KEY_IS_REGISTERED, false)

    fun register(context: Context, name: String, password: String) {
        prefs(context).edit()
            .putString(KEY_USER_NAME, name)
            .putString(KEY_USER_PASSWORD, password)
            .putBoolean(KEY_IS_REGISTERED, true)
            .apply()
    }

    fun logout(context: Context) {
        prefs(context).edit()
            .putBoolean(KEY_IS_REGISTERED, false)
            .remove(KEY_CARD_LINKED)
            .remove(KEY_CARD_LAST4)
            .remove(KEY_BALANCE)
            .apply()
    }

    fun getBalance(context: Context): Int =
        prefs(context).getInt(KEY_BALANCE, 0)

    fun setBalance(context: Context, amount: Int) {
        prefs(context).edit().putInt(KEY_BALANCE, amount.coerceAtLeast(0)).apply()
    }

    fun addBalance(context: Context, amount: Int) {
        val newBalance = (getBalance(context) + amount).coerceAtLeast(0)
        setBalance(context, newBalance)
    }

    fun getUserName(context: Context): String? =
        prefs(context).getString(KEY_USER_NAME, null)

    fun getPassword(context: Context): String? =
        prefs(context).getString(KEY_USER_PASSWORD, null)

    fun getSelectedAddressIndex(context: Context): Int =
        prefs(context).getInt(KEY_SELECTED_ADDRESS_INDEX, 0)

    fun setSelectedAddressIndex(context: Context, index: Int) {
        prefs(context).edit().putInt(KEY_SELECTED_ADDRESS_INDEX, index).apply()
    }

    fun isDarkTheme(context: Context): Boolean =
        prefs(context).getBoolean(KEY_DARK_THEME, false)

    fun setDarkTheme(context: Context, enabled: Boolean) {
        prefs(context).edit().putBoolean(KEY_DARK_THEME, enabled).apply()
        AppCompatDelegate.setDefaultNightMode(
            if (enabled) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    fun isCardLinked(context: Context): Boolean =
        prefs(context).getBoolean(KEY_CARD_LINKED, false)

    fun getCardLast4(context: Context): String? =
        prefs(context).getString(KEY_CARD_LAST4, null)

    fun linkCard(context: Context, last4: String) {
        prefs(context).edit()
            .putBoolean(KEY_CARD_LINKED, true)
            .putString(KEY_CARD_LAST4, last4)
            .apply()
    }

    fun unlinkCard(context: Context) {
        prefs(context).edit()
            .remove(KEY_CARD_LINKED)
            .remove(KEY_CARD_LAST4)
            .apply()
    }
}
