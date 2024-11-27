package com.example.money_manager_app.data.model

open class SubTransaction(
    override val id: Long,
    override val iconId: Int,
    override val name: String,
    override val amount: Double,
    override val colorId: Int,
    override val accountId: Long,
    override val walletId: Long,
    override val date: Long,
    override val time: Long,
) : Transaction(id, iconId, name, amount, colorId, accountId, walletId, date, time)