package com.example.money_manager_app.data.model

import com.example.money_manager_app.data.model.entity.Wallet

data class WalletItem(
    val wallet: Wallet,
    val currentAmount: Double,
)

fun WalletItem.toWallet() = wallet