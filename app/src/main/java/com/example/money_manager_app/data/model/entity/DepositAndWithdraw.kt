package com.example.money_manager_app.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.money_manager_app.data.model.entity.enums.GoalInputType
import com.example.money_manager_app.utils.GoalInputTypeConverter

@Entity(
    tableName = "deposit_and_withdraw", foreignKeys = [ForeignKey(
        entity = Goal::class,
        parentColumns = ["id"],
        childColumns = ["goal_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class DepositAndWithdraw(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    @TypeConverters(GoalInputTypeConverter::class) val type: GoalInputType,
    @ColumnInfo(name = "goal_id") val goalId: Long
)