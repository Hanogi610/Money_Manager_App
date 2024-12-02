package com.example.money_manager_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.money_manager_app.data.model.entity.Budget
import com.example.money_manager_app.data.model.entity.BudgetCategoryCrossRef
import com.example.money_manager_app.data.model.entity.BudgetWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBudget(budget: Budget) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editBudget(budget: Budget)

    @Delete
    fun deleteBudget(budget: Budget)

    @Query("DELETE FROM budget WHERE budget_id = :id")
    fun deleteBudget(id: Long)

    @Query("SELECT * FROM budget WHERE account_id = :accountId")
    fun getBudgetsByAccountId(accountId: Long): Flow<List<BudgetWithCategory>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBudgetCategoryCrossRefs(crossRefs: List<BudgetCategoryCrossRef>)

    @Query("DELETE FROM BudgetCategoryCrossRef WHERE budget_id = :budgetId AND category_id = :categoryId")
    suspend fun deleteBudgetCategoryCrossRef(budgetId: Long, categoryId: Long)

}
