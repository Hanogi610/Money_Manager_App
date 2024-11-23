package com.example.money_manager_app.data.model

open class SubTransaction(
    override val id: Long,
    override val name: String,
    override val amount: Double,
    override val accountId: Long,
    open val walletId: Long,
    open val date: Long?,
    open val time: Long?,
) : Transaction(id, name, amount, accountId)