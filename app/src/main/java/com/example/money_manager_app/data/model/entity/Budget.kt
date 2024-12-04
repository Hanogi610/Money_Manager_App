package com.example.money_manager_app.data.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.entity.enums.PeriodType
import com.example.money_manager_app.utils.PeriodTypeConverter
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "budget", foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["account_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Budget(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "budget_id") val id: Long = 0,
    val name: String,
    val amount: Double,
    var spent : Int,
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "color_id") val colorId: Int? = R.color.color_1,
    @ColumnInfo(name = "period_type") @TypeConverters(PeriodTypeConverter::class) val periodType: PeriodType,
    val start_date : Long,
    val end_date : Long
) : Parcelable
