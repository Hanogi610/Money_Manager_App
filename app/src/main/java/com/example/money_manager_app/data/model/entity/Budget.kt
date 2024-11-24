package com.example.money_manager_app.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.entity.Account
import com.example.money_manager_app.data.model.entity.enums.CategoryType
import com.example.money_manager_app.data.model.entity.enums.PeriodType
import com.example.money_manager_app.utils.CategoryTypeConverter
import com.example.money_manager_app.utils.PeriodTypeConverter

@Entity(
    tableName = "budget", foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["account_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Budget(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "account_id") val accountId: Long,
    val name: String,
    @ColumnInfo(name = "color_id") val colorId: Int ? = R.color.color_1,
    val amount: Double,
    @ColumnInfo(name = "period_date_start") val periodDateStart: Long,
    @ColumnInfo(name = "period_date_end") val periodDateEnd: Long,
    @ColumnInfo(name = "period_type")
    @TypeConverters(PeriodTypeConverter::class)
    val periodType: PeriodType,
    @ColumnInfo(name = "category_type")
    @TypeConverters(CategoryTypeConverter::class)
    val categoryType: CategoryType
)
//