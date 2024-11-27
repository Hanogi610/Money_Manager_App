package com.example.money_manager_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.money_manager_app.data.model.entity.Transfer
import com.example.money_manager_app.data.model.entity.enums.CategoryType

@Dao
interface TransferDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTransfer(transfer: Transfer) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editTransfer(transfer: Transfer)

    @Delete
    suspend fun deleteTransfer(transfer: Transfer)

    @Query("DELETE FROM transfer WHERE id = :id")
    suspend fun deleteTransfer(id: Long)

    @Query("SELECT * FROM transfer WHERE account_id = :accountId")
    fun getTransfersByAccountId(accountId: Long): List<Transfer>

    @Query("SELECT * FROM transfer WHERE account_id = :accountId AND category = :categoryType")
    fun getTransfersByAccountIdAndCategory(accountId: Long, categoryType: CategoryType): List<Transfer>
}
