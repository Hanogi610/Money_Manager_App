package com.example.money_manager_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.money_manager_app.data.model.entity.Transfer
import kotlinx.coroutines.flow.Flow
import com.example.money_manager_app.data.model.entity.enums.CategoryType

@Dao
interface TransferDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransfer(transfer: Transfer): Long

    @Query("SELECT * FROM transfer WHERE transfer_date BETWEEN :startDay AND :endDay AND account_id = :accountId")
    fun getTransferFromDayStartAndDayEnd(startDay : Long, endDay : Long, accountId : Long): Flow<List<Transfer>>

    @Query("SELECT * FROM transfer WHERE transfer_date = :date")
    fun getAllTransfer(date : Long): Flow<List<Transfer>>
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
