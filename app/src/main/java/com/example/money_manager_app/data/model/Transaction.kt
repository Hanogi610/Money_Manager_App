package com.example.money_manager_app.data.model

open class Transaction(
    open val id: Long,
    open val name: String,
    open val amount: Double,
    open val accountId: Long,
)

