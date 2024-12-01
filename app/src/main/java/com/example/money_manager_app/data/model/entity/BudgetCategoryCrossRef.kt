package com.example.money_manager_app.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    primaryKeys = ["transfer_id", "category_id"], foreignKeys = [ForeignKey(
        entity = Budget::class,
        parentColumns = ["budget_id"],
        childColumns = ["budget_id"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Category::class,
        parentColumns = ["category_id"],
        childColumns = ["category_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class BudgetCategoryCrossRef(
    @ColumnInfo(name = "budget_id") val budgetId: Long,
    @ColumnInfo(name = "category_id") val categoryId: Long
)

data class BudgetWithCategory(
    @Embedded val budget: Budget,
    @Relation(
        parentColumn = "transfer_id",
        entityColumn = "category_id",
        associateBy = Junction(BudgetCategoryCrossRef::class)
    )
    val categories: List<Category>
)