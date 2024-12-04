package com.example.money_manager_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.money_manager_app.data.model.entity.Debt
import com.example.money_manager_app.data.model.entity.DebtTransaction
import kotlinx.coroutines.flow.Flow

@Dao
interface DebtTransactionDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertDebtTransaction(debtTransaction: DebtTransaction) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editDebtTransaction(debtTransaction: DebtTransaction)

    @Query("SELECT * FROM debt_transaction WHERE debt_id = :debtId")
    fun getDebtTransactionsByDebtId(debtId: Long) : Flow<List<DebtTransaction>>

    @Query("SELECT * FROM debt_transaction WHERE account_id = :accountId")
    fun getDebtTransactionsByAccountId(accountId: Long) : Flow<List<DebtTransaction>>

    @Query("DELETE FROM debt_transaction WHERE id = :debtTransactionId")
    suspend fun deleteDebtTransaction(debtTransactionId: Long)

    @Query(" SELECT * FROM debt_transaction WHERE date BETWEEN :startDay AND :endDay AND account_id = :accountId")
    fun getDebtTransactionFromDayStartAndDayEnd(accountId: Long, startDay : Long, endDay : Long): Flow<List<DebtTransaction>>

    @Query("""
    SELECT * FROM debt_transaction
    WHERE (:startDate IS NULL OR date >= :startDate)
    AND (:endDate IS NULL OR date <= :endDate)
    AND (:minAmount IS NULL OR amount >= :minAmount)
    AND (:maxAmount IS NULL OR amount <= :maxAmount)
    AND (:categoryType IS NULL OR icon_id = :categoryType)
    AND (:fromWallet IS NULL OR wallet_id = :fromWallet)
""")
    fun searchByDateAndAmountAndDesAndCategoryAndWallet(
        startDate : Long?,
        endDate : Long?,
        minAmount : Double?,
        maxAmount : Double?,
        categoryType: Int?,
        fromWallet : Long?
    ): List<DebtTransaction>

    @Query("SELECT * FROM debt_transaction WHERE date = :date AND account_id = :accountId")
    fun getDebtTransactionsByDateAndAccountId(
        date: Long,
        accountId: Long
    ): Flow<List<DebtTransaction>>

    @Delete
    suspend fun deleteDebtTransaction(debtTransaction: DebtTransaction)

    @Query("SELECT * FROM debt_transaction WHERE account_id = :accountId AND wallet_id = :walletId")
    fun getDebtTransactionsByAccountIdAndWalletId(accountId: Long, walletId: Long) : Flow<List<DebtTransaction>>
}