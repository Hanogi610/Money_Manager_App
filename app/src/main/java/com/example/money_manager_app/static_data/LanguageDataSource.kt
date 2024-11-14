package com.example.money_manager_app.static_data

import com.example.money_manager_app.R
import com.example.money_manager_app.model.LanguageModel

object LanguageDataSource {
    fun getLanguageList() = listOf(
        LanguageModel(R.drawable.us_flag, "English", "en", true),
        LanguageModel(R.drawable.us_flag, "China", "zh"),
        LanguageModel(R.drawable.us_flag, "India", "hi"),
        LanguageModel(R.drawable.vn_flag, "Vietnam", "vi")
    )
}