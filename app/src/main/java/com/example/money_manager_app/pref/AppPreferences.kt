package com.example.money_manager_app.pref

import javax.inject.Singleton


@Singleton
interface AppPreferences {

    fun get(key: String): String?

    fun get(key: String, default: Int): Int

    fun get(key: String, default: Boolean): Boolean

    fun put(key: String, value: String)

    fun put(key: String, value: Int)

    fun put(key: String, value: Boolean)

    fun clear()

    fun remove(key: String)

    fun isFirstTimeLaunch() : Boolean

    fun setFirstTimeLaunch(isFirstTimeLaunch: Boolean)

    fun getLanguage() : String

    fun setLanguage(language: String)
}