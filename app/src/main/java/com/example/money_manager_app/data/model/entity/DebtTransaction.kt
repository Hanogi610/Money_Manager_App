package com.example.money_manager_app.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.money_manager_app.data.model.SubTransaction
import com.example.money_manager_app.data.model.entity.enums.DebtActionType
import com.example.money_manager_app.utils.DebtActionTypeConverter

@Entity(
    tableName = "debt_transaction", foreignKeys = [ForeignKey(
        entity = Debt::class,
        parentColumns = ["id"],
        childColumns = ["debt_id"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Wallet::class,
        parentColumns = ["id"],
        childColumns = ["wallet_id"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["account_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class DebtTransaction(
    @PrimaryKey(autoGenerate = true) override val id: Long = 0,
    @ColumnInfo(name = "icon_id") override val iconId: Int,
    override val name: String,
    @ColumnInfo(name = "color_id") override val colorId: Int,
    @ColumnInfo(name = "account_id") override val accountId: Long,
    @ColumnInfo(name = "debt_id") val debtId: Long,
    @ColumnInfo(name = "wallet_id") override val walletId: Long,
    override val amount: Double,
    @TypeConverters(DebtActionTypeConverter::class) val action: DebtActionType,
    override val date: Long = System.currentTimeMillis(),
    override val time: Long = System.currentTimeMillis(),
) : SubTransaction(id, iconId, name, amount, colorId, accountId, walletId, date, time)