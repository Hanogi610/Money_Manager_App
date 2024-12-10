package com.example.money_manager_app.data.model.entity

import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.entity.enums.WalletType
import com.example.money_manager_app.utils.WalletTypeConverter
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "wallet", foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["account_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Wallet(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "account_id") val accountId: Long,
    @TypeConverters(WalletTypeConverter::class) @ColumnInfo(name = "wallet_type") val walletType: WalletType,
    @ColumnInfo(name = "name") val name: String,
    @DrawableRes @ColumnInfo(name = "icon_id") val iconId: Int = R.drawable.wallet_14,
    @ColorRes @ColumnInfo(name = "color_id") val colorId: Int = R.color.color_1,
    @ColumnInfo(name = "is_excluded") val isExcluded: Boolean? = null,
    @ColumnInfo(name = "statement_date") val statementDate: Long? = null,
    @ColumnInfo(name = "due_date") val dueDate: Long? = null,
) : Parcelable