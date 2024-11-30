package com.example.money_manager_app.data.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.money_manager_app.data.dao.BudgetDao
import com.example.money_manager_app.data.model.entity.Budget
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface BudgetRepository {
    suspend fun insertBudget(budget: Budget) : Long

    suspend fun editBudget(budget: Budget)

    fun deleteBudget(budget: Budget)

    fun deleteBudget(id: Long)

    fun getBudgetsByAccountId(accountId: Long): Flow<List<Budget>>
}

class BudgetRepositoryImpl @Inject constructor(
    private val budgetDao: BudgetDao
) : BudgetRepository {
    override suspend fun insertBudget(budget: Budget): Long {
        return budgetDao.insertBudget(budget)
    }

    override suspend fun editBudget(budget: Budget) {
        budgetDao.editBudget(budget)
    }

    override fun deleteBudget(budget: Budget) {
        budgetDao.deleteBudget(budget)
    }

    override fun deleteBudget(id: Long) {
        budgetDao.deleteBudget(id)
    }

    override fun getBudgetsByAccountId(accountId: Long): Flow<List<Budget>> {
        return budgetDao.getBudgetsByAccountId(accountId)
    }

}