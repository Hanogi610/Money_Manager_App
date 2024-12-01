package com.example.money_manager_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.money_manager_app.data.model.entity.Transfer
import kotlinx.coroutines.flow.Flow

@Dao
interface TransferDao {

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

    @Query("""
    SELECT * FROM transfer
    WHERE (:startDate IS NULL OR transfer_date >= :startDate)
    AND (:endDate IS NULL OR transfer_date <= :endDate)
    AND (:minAmount IS NULL OR amount >= :minAmount)
    AND (:maxAmount IS NULL OR amount <= :maxAmount)
    AND (:description IS NULL OR description LIKE '%' || :description || '%')
    AND (:categoryType IS NULL OR type_icon_category = :categoryType)
    AND (:fromWallet IS NULL OR from_wallet = :fromWallet)
""")
    fun searchByDateAndAmountAndDesAndCategoryAndWallet(
        startDate : Long?,
        endDate : Long?,
        minAmount : Double?,
        maxAmount : Double?,
        description : String?,
        categoryType: Int?,
        fromWallet : Long?
    ): List<Transfer>

//
//    @Query("SELECT * FROM transfer WHERE account_id = :accountId AND iconId = :categoryType")
//    fun getTransfersByAccountIdAndCategory(accountId: Long, categoryType: CategoryType): List<Transfer>
}
