package com.example.money_manager_app

import com.example.money_manager_app.base.BaseApplication
import com.example.money_manager_app.fragment.LocaleHelper
import com.example.money_manager_app.pref.AppPreferences
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class MoneyManagerApplication : BaseApplication() {
    @Inject
    lateinit var appPreferences: AppPreferences
    override fun onCreate() {
        super.onCreate()
        setAppLocale()
//        createChannelNotification(this)
    }
    private fun setAppLocale() {
        runBlocking {
            val languageCode = appPreferences.getLanguage().firstOrNull()
            languageCode?.let {
                LocaleHelper.setLocale(this@MoneyManagerApplication, languageCode)
            }
        }
    }
}