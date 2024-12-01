package com.example.money_manager_app.data.model

import com.example.money_manager_app.data.model.entity.Category
import com.example.money_manager_app.data.model.entity.TransferWithCategory
import com.example.money_manager_app.data.model.entity.enums.TransferType

data class TransferDetail(
    override val id : Long = 0,
    override val walletId: Long,
    val toWalletId: Long,
    override val amount: Double,
    override val name : String,
    val fee: Double,
    val description: String,
    override val accountId: Long,
    val linkImg: String,
    override val date: Long = System.currentTimeMillis(),
    override val time: Long = System.currentTimeMillis(),
    val typeOfExpenditure: TransferType,
    override val iconId: Int?,
    val categories: List<Category>
): Transaction(id, iconId, name, amount, accountId, walletId, date, time)

fun TransferWithCategory.toTransferDetail(): TransferDetail {
    return TransferDetail(
        id = transfer.id,
        walletId = transfer.walletId,
        toWalletId = transfer.toWalletId,
        amount = transfer.amount,
        name = transfer.name,
        fee = transfer.fee,
        description = transfer.description,
        accountId = transfer.accountId,
        linkImg = transfer.linkImg,
        date = transfer.date,
        time = transfer.time,
        typeOfExpenditure = transfer.typeOfExpenditure,
        iconId = transfer.iconId,
        categories = categories
    )
}