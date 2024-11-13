package com.example.money_manager_app.static_data

import com.example.money_manager_app.R
import com.example.money_manager_app.model.LanguageModel

object LanguageDataSource {
    fun getLanguageList() = listOf(
        LanguageModel(R.drawable.ic_english, "English", "en", true),
        LanguageModel(R.drawable.ic_china, "China", "zh"),
        LanguageModel(R.drawable.ic_india, "India", "hi"),
        LanguageModel(R.drawable.ic_vietnam, "Vietnam", "vi")
    )
}