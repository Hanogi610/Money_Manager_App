package com.example.money_manager_app.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    primaryKeys = ["transfer_id", "category_id"], foreignKeys = [ForeignKey(
        entity = Transfer::class,
        parentColumns = ["transfer_id"],
        childColumns = ["transfer_id"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Category::class,
        parentColumns = ["category_id"],
        childColumns = ["category_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TransferCategoryCrossRef(
    @ColumnInfo(name = "transfer_id") val transferId: Long,
    @ColumnInfo(name = "category_id") val categoryId: Long
)

data class TransferWithCategory(
    @Embedded val transfer: Transfer,
    @Relation(
        parentColumn = "transfer_id",
        entityColumn = "category_id",
        associateBy = Junction(TransferCategoryCrossRef::class)
    )
    val categories: List<Category>
)

data class CategoryWithTransfer(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "transfer_id",
        associateBy = Junction(TransferCategoryCrossRef::class)
    )
    val transfers: List<Transfer>
)