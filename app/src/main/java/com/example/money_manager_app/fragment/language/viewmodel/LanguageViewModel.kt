package com.example.money_manager_app.fragment.language.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.money_manager_app.base.BaseViewModel
import com.example.money_manager_app.datasource.LanguageDataSource
import com.example.money_manager_app.fragment.language.LocaleHelper
import com.example.money_manager_app.model.LanguageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltViewModel
class LanguageViewModel(
    @ApplicationContext private val context: Context
) : BaseViewModel() {
    private val _languages: MutableLiveData<List<LanguageModel>> = MutableLiveData()
    val languages: MutableLiveData<List<LanguageModel>> = _languages

    private var currentLanguage: String = "en"

    init {
        _languages.postValue(LanguageDataSource.getLanguageList())
    }

    fun selectLanguage(language: String) {
        val languages = _languages.value ?: return

        languages.forEach {
            it.isCheck = it.locale == language
        }

        currentLanguage = language

        _languages.postValue(languages)
    }

    fun changeLanguage() : String {
        LocaleHelper.setLocale(context, currentLanguage)
        return currentLanguage
    }
}