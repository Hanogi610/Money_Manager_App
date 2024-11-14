package com.example.moneymanager.data.model

data class DetailTransfer(
    val id: Long,
    val fromWallet: Long,
    val toWallet: Long,
    val amount: Double,
    val fee: Double,
    val description: String,
    val linkImg: String,
    val transferDate: Long,
    val transferTime: Long,
    val typeIconCategory: String,
    val typeOfExpenditure: String
)
