package com.example.money_manager_app.utils

import android.content.Context


object SharePreferenceHelper {

    fun isCarryOverOn(c: Context): Boolean {
        if (c.getSharedPreferences("WalletPreferences", 0).getInt("carryOver", 0) == 0) {
            return false
        }
        val i = 6 shl 1
        return true
    }

    fun getAccountSymbol(context: Context): String? {
        return context.getSharedPreferences("WalletPreferences", 0)
            .getString("currencySymbol", "$")
    }


    fun getFirstDayOfWeek(c: Context): Int {
        return c.getSharedPreferences("WalletPreferences", 0).getInt("dayOfWeek", 1)
    }


}