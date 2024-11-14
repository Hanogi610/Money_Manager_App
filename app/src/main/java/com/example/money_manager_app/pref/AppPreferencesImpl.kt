package com.example.money_manager_app.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.money_manager_app.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppPreferencesImpl @Inject constructor(
    @ApplicationContext context: Context
) : AppPreferences {

    companion object {
        const val PREF_PARAM_IS_FIRST_TIME_LAUNCH = "IS_FIRST_TIME_LAUNCH"
        const val PREF_PARAM_LANGUAGE = "LANGUAGE"
    }

    private var masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val mPrefs: SharedPreferences = EncryptedSharedPreferences.create(
        Constants.PREF_FILE_NAME,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun put(key: String, value: String) {
        val editor: SharedPreferences.Editor = mPrefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun get(key: String): String? {
        return mPrefs.getString(key, "")
    }

    override fun put(key: String, value: Int) {
        val editor: SharedPreferences.Editor = mPrefs.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    override fun get(key: String, default: Int) = mPrefs.getInt(key, default)

    override fun put(key: String, value: Boolean) {
        val editor: SharedPreferences.Editor = mPrefs.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    override fun get(key: String, default: Boolean) = mPrefs.getBoolean(key, default)

    override fun clear() {
        val editor: SharedPreferences.Editor = mPrefs.edit()
        editor.clear()
        editor.apply()
    }

    override fun remove(key: String) {
        val editor: SharedPreferences.Editor = mPrefs.edit()
        editor.remove(key)
        editor.apply()
    }

    override fun isFirstTimeLaunch() = get(PREF_PARAM_IS_FIRST_TIME_LAUNCH, false)

    override fun setFirstTimeLaunch(isFirstTimeLaunch: Boolean) {
        put(PREF_PARAM_IS_FIRST_TIME_LAUNCH, isFirstTimeLaunch)
    }

    override fun getLanguage(): String = get(PREF_PARAM_LANGUAGE) ?: "en"

    override fun setLanguage(language: String) {
        put(PREF_PARAM_LANGUAGE, language)
    }
}