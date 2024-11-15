package com.example.money_manager_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.money_manager_app.data.model.entity.Debt
import com.example.money_manager_app.data.model.entity.DebtDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface DebtDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertDebt(debt: Debt) : Long

    @Query("SELECT * FROM debt WHERE account_id = :accountId")
    fun getDebtsByAccountId(accountId: Long): Flow<List<DebtDetail>>

    @Query("SELECT * FROM debt WHERE id = :debtId")
    fun getDebtDetailsByDebtId(debtId: Long): Flow<DebtDetail>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editDebt(debt: Debt)

    @Query("DELETE FROM debt WHERE id = :debtId")
    suspend fun deleteDebt(debtId: Long)

    @Delete
    suspend fun deleteDebt(debt: Debt)
}