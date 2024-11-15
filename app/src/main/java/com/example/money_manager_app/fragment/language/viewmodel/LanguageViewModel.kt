package com.example.money_manager_app.fragment.language.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.money_manager_app.base.BaseViewModel
import com.example.money_manager_app.datasource.LanguageDataSource
import com.example.money_manager_app.fragment.language.LocaleHelper
import com.example.money_manager_app.model.LanguageModel
import com.example.money_manager_app.pref.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltViewModel
class LanguageViewModel(
    @ApplicationContext private val context: Context,
    private val appPreferences: AppPreferences
) : BaseViewModel() {
    private val _languages: MutableLiveData<List<LanguageModel>> = MutableLiveData()
    val languages: MutableLiveData<List<LanguageModel>> = _languages

    private var currentLanguage: String = "en"

    init {
        currentLanguage = appPreferences.getLanguage()
        _languages.postValue(
            LanguageDataSource.getLanguageList().map {
                it.apply { isCheck = locale == currentLanguage }
            }
        )
    }

    fun selectLanguage(language: String) {
        val languages = _languages.value ?: return

        languages.forEach {
            it.isCheck = it.locale == language
        }

        currentLanguage = language

        _languages.postValue(languages)
    }

    fun changeLanguage() {
        LocaleHelper.setLocale(context, currentLanguage)
        appPreferences.setLanguage(currentLanguage)
        appPreferences.setFirstTimeLaunch(false)
    }
}