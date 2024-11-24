package com.example.money_manager_app.data.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.money_manager_app.data.model.Transaction
import kotlinx.parcelize.Parcelize

@Parcelize
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
    val name: String,
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "target_date") val targetDate: Long,
    @ColumnInfo(name = "color_id") val colorId: Int,
    @ColumnInfo(name = "goal_amount") val amount: Double,
) : Parcelable

data class GoalDetail(
    @Embedded val goal: Goal, @Relation(
        parentColumn = "id", entityColumn = "goal_id"
    ) val transactions: List<GoalTransaction>
)