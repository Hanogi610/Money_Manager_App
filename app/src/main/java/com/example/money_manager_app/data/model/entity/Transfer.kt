package com.example.money_manager_app.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.money_manager_app.data.model.Transaction
import com.example.money_manager_app.data.model.entity.enums.TransferType

@Entity(
    tableName = "transfer" , foreignKeys = [
        ForeignKey(
            entity = Wallet::class,
            parentColumns = ["id"],
            childColumns = ["from_wallet_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Wallet::class,
            parentColumns = ["id"],
            childColumns = ["to_wallet_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Account::class,
            parentColumns = ["id"],
            childColumns = ["account_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class Transfer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transfer_id") override val id : Long = 0,
    @ColumnInfo(name = "from_wallet_id") override val walletId: Long,
    @ColumnInfo(name = "to_wallet_id") val toWalletId: Long,
    override val amount: Double,
    override val name : String,
    val fee: Double,
    val description: String,
    @ColumnInfo(name = "account_id") override val accountId: Long,
    @ColumnInfo(name = "link_img") val linkImg: String,
    override val date: Long = System.currentTimeMillis(),
    override val time: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "type_of_expenditure") val typeOfExpenditure: TransferType,
    @ColumnInfo(name = "icon_id") override val iconId: Int?,
) : Transaction(id, iconId, name, amount, accountId, walletId, date, time)