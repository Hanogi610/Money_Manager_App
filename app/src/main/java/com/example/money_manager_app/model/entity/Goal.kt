package com.example.moneymanager.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "goal", foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["account_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Goal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "target_date") val targetDate: Long,
    @ColumnInfo(name = "type_color") val typeColor: String,
    @ColumnInfo(name = "goal_amount") val goalAmount: Double,
)