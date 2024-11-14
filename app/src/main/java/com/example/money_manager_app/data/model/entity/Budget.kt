package com.example.money_manager_app.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.money_manager_app.data.model.entity.Account

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
    @ColumnInfo(name = "type_color") val typeColor: String,
    @ColumnInfo(name = "initial_amount") val initialAmount: Double,
    @ColumnInfo(name = "period_date_start") val periodDateStart: Long,
    @ColumnInfo(name = "period_date_end") val periodDateEnd: Long,
    val period: String,
    @ColumnInfo(name = "icon_category") val iconCategory: String
)
