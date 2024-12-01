package com.example.money_manager_app.data.repository

import com.example.money_manager_app.data.dao.BudgetDao
import com.example.money_manager_app.data.model.BudgetDetail
import com.example.money_manager_app.data.model.entity.Budget
import com.example.money_manager_app.data.model.entity.BudgetCategoryCrossRef
import com.example.money_manager_app.data.model.toBudgetDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface BudgetRepository {
    suspend fun insertBudget(budgetDetail: BudgetDetail): Long

    suspend fun editBudget(budget: Budget)

    fun deleteBudget(budget: Budget)

    fun deleteBudget(id: Long)

    fun getBudgetsByAccountId(accountId: Long): Flow<List<BudgetDetail>>

    suspend fun removeCategoryFromBudget(budgetId: Long, categoryId: Long)
}

class BudgetRepositoryImpl @Inject constructor(
    private val budgetDao: BudgetDao
) : BudgetRepository {
    override suspend fun insertBudget(budgetDetail: BudgetDetail): Long {
        // Convert BudgetDetail to Budget entity
        val budget = Budget(
            id = budgetDetail.id,
            accountId = budgetDetail.accountId,
            name = budgetDetail.name,
            colorId = budgetDetail.colorId,
            amount = budgetDetail.amount,
            periodDateStart = budgetDetail.periodDateStart,
            periodType = budgetDetail.periodType
        )
        val budgetId = budgetDao.insertBudget(budget)

        // Insert associated categories
        val budgetCategoryCrossRefs = budgetDetail.categories.map { category ->
            BudgetCategoryCrossRef(budgetId, category.id)
        }
        budgetDao.insertBudgetCategoryCrossRefs(budgetCategoryCrossRefs)
        return budgetId
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

    override fun getBudgetsByAccountId(accountId: Long): Flow<List<BudgetDetail>> {
        return budgetDao.getBudgetsByAccountId(accountId).map {
            it.map { it.toBudgetDetail() }
        }
    }

    override suspend fun removeCategoryFromBudget(budgetId: Long, categoryId: Long) {
        budgetDao.deleteBudgetCategoryCrossRef(budgetId, categoryId)
    }

}