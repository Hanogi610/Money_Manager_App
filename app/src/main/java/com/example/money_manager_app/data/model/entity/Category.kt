package com.example.money_manager_app.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import com.example.money_manager_app.data.model.entity.enums.CategoryType
import com.example.money_manager_app.utils.CategoryTypeConverter

@Entity(
    tableName = "category", foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["account_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id") val id: Long,
    val name: String,
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "icon_id") val iconId: Int,
    @ColumnInfo(name = "color_id") val colorId: Int,
    @TypeConverters(CategoryTypeConverter::class)
    @ColumnInfo(name = "category_type") val type: CategoryType
)


data class CategoryWithTransfer(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "transfer_id",
        entityColumn = "category_id"
    )
    val transfers: List<Transfer>
)