package com.example.money_manager_app.data.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.Transaction
import com.example.money_manager_app.data.model.entity.enums.DebtType
import com.example.money_manager_app.utils.DebtTypeConverter
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "debt", foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["account_id"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Wallet::class,
        parentColumns = ["id"],
        childColumns = ["wallet_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Debt(
    @PrimaryKey(autoGenerate = true) override val id: Long = 0,
    @ColumnInfo(name = "icon_id") override val iconId: Int = R.drawable.wallet_6,
    override val name: String,
    override val amount: Double,
    @ColumnInfo(name = "account_id") override val accountId: Long,
    @TypeConverters(DebtTypeConverter::class) val type: DebtType,
    override val date: Long,
    val time: Long,
    val description: String,
    @ColumnInfo(name = "wallet_id")override val walletId: Long,
    @ColumnInfo(name = "color_id")override val colorId: Int = R.color.color_1,
) : Transaction(id, iconId, name, amount, colorId, accountId, walletId, date), Parcelable

data class DebtDetail(
    @Embedded val debt: Debt,
    @Relation(
        parentColumn = "id",
        entityColumn = "debt_id"
    )
    val transactions: List<DebtTransaction>
)
