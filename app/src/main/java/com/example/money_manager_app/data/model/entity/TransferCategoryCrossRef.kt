package com.example.money_manager_app.data.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    primaryKeys = ["transferId", "categoryId"], foreignKeys = [ForeignKey(
        entity = Transfer::class,
        parentColumns = ["id"],
        childColumns = ["transferId"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TransferCategoryCrossRef(
    val transferId: Long, val categoryId: Long
)

data class TransferWithCategory(
    @Embedded val transfer: Transfer,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId",
        associateBy = Junction(TransferCategoryCrossRef::class)
    )
    val categories: List<Category>
)

data class CategoryWithTransfer(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "transferId",
        associateBy = Junction(TransferCategoryCrossRef::class)
    )
    val transfers: List<Transfer>
)