package com.example.money_manager_app.pref

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext context: Context
) : BasePreferencesImpl(context) {

    private object PreferencesKeys {
        val PREF_PARAM_LANGUAGE = stringPreferencesKey("PREF_PARAM_LANGUAGE")
        val PREF_PARAM_FIRST_TIME = booleanPreferencesKey("PREF_PARAM_FIRST_TIME")
    }

    fun getLanguage(): Flow<String?> = getValue(PreferencesKeys.PREF_PARAM_LANGUAGE)

    suspend fun setLanguage(locale: String) {
        putValue(PreferencesKeys.PREF_PARAM_LANGUAGE, locale)
    }

    fun getIsFirstTime(): Flow<Boolean?> = getValue(PreferencesKeys.PREF_PARAM_FIRST_TIME)

    suspend fun setIsFirstTime(isFirstTime: Boolean) {
        putValue(PreferencesKeys.PREF_PARAM_FIRST_TIME, isFirstTime)
    }
}