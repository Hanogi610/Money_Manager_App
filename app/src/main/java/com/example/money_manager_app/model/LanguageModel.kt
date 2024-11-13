package com.example.money_manager_app.model

data class LanguageModel (
    val flag: Int,
    val languageName: String,
    val locale: String,
    var isCheck: Boolean = false
)
