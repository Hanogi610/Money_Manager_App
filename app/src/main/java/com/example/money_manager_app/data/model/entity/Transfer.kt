package com.example.money_manager_app.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.money_manager_app.data.model.Transaction
import com.example.money_manager_app.data.model.entity.enums.TransferType

@Entity(
    tableName = "transfer" , foreignKeys = [
        ForeignKey(
            entity = Wallet::class,
            parentColumns = ["id"],
            childColumns = ["from_wallet"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Wallet::class,
            parentColumns = ["id"],
            childColumns = ["to_wallet"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Transfer(
    @PrimaryKey(autoGenerate = true) override val  id : Long = 0,
    @ColumnInfo(name = "from_wallet") override val walletId: Long,
    @ColumnInfo(name = "to_wallet") val toWallet: Long,
    override val amount: Double,
    override val name : String,
    val fee: Double,
    val description: String,
    @ColumnInfo(name = "account_id") override val accountId: Long,
    @ColumnInfo(name = "link_img") val linkImg: String,
    @ColumnInfo(name = "transfer_date") override val date: Long,
    @ColumnInfo(name = "transfer_time") val transferTime: Long,
    @ColumnInfo(name = "type_of_expenditure") val typeOfExpenditure: TransferType,
    @ColumnInfo(name = "type_debt") val typeDebt: String,
    @ColumnInfo(name = "type_icon_category") override val iconId: Int,
    @ColumnInfo(name = "type_color") override val colorId: Int,
    @ColumnInfo(name = "type_icon_wallet") val typeIconWallet: String
) : Transaction(id, iconId, name, amount, colorId, accountId, walletId, date)
