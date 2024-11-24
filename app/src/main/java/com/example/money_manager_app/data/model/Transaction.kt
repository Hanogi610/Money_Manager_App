package com.example.money_manager_app.data.model

open class Transaction(
    open val id: Long,
    open val iconId: Int?,
    open val name: String,
    open val amount: Double,
    open val colorId: Int?,
    open val accountId: Long,
    open val walletId: Long,
    open val date: Long,
)

