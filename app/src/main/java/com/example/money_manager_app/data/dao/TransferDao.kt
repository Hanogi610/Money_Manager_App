package com.example.money_manager_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.money_manager_app.data.model.entity.BudgetCategoryCrossRef
import com.example.money_manager_app.data.model.entity.Transfer
import kotlinx.coroutines.flow.Flow

@Dao
interface TransferDao {

    @Query("SELECT * FROM transfer WHERE date BETWEEN :startDay AND :endDay AND account_id = :accountId")
    fun getTransferFromDayStartAndDayEnd(
        startDay: Long, endDay: Long, accountId: Long
    ): Flow<List<Transfer>>

    @Query("SELECT * FROM transfer WHERE date = :date")
    fun getAllTransfer(date: Long): Flow<List<Transfer>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTransfer(transfer: Transfer): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTransferCategoryCrossRefs(crossRefs: List<BudgetCategoryCrossRef>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editTransfer(transfer: Transfer)

    @Delete
    suspend fun deleteTransfer(transfer: Transfer)

    @Query("DELETE FROM transfer WHERE transfer_id = :id")
    suspend fun deleteTransfer(id: Long)

    @Query("SELECT * FROM transfer WHERE account_id = :accountId")
    fun getTransfersByAccountId(accountId: Long): Flow<List<Transfer>>

    @Query(
        """
    SELECT * FROM transfer
    WHERE (:startDate IS NULL OR date >= :startDate)
    AND (:endDate IS NULL OR date <= :endDate)
    AND (:minAmount IS NULL OR amount >= :minAmount)
    AND (:maxAmount IS NULL OR amount <= :maxAmount)
    AND (:description IS NULL OR description LIKE '%' || :description || '%')
    AND (:fromWalletId IS NULL OR from_wallet_id = :fromWalletId)
"""
    )
    fun searchByDateAndAmountAndDesAndCategoryAndWallet(
        startDate: Long?,
        endDate: Long?,
        minAmount: Double?,
        maxAmount: Double?,
        description: String?,
        fromWalletId: Long?
    ): List<Transfer>

    @Query("SELECT * FROM transfer WHERE account_id = :accountId")
    fun getTransferWithCategoryByAccountId(accountId: Long): Flow<List<Transfer>>
}