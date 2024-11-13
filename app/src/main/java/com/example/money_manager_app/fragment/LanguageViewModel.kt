package com.example.money_manager_app.fragment

import androidx.lifecycle.MutableLiveData
import com.example.money_manager_app.base.BaseViewModel

class LanguageViewModel : BaseViewModel() {
    private val _isSelectLanguage = MutableLiveData<Boolean>()
    val isSelectLanguage: MutableLiveData<Boolean> = _isSelectLanguage

    fun selectLanguage() {
        if (_isSelectLanguage.value == true) {
            return
        }

        _isSelectLanguage.value = true
    }
}