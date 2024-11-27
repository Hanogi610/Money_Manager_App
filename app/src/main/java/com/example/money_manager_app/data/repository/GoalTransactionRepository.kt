package com.example.money_manager_app.data.repository

import com.example.money_manager_app.data.dao.GoalTransactionDao
import com.example.money_manager_app.data.model.entity.GoalTransaction
import javax.inject.Inject

interface GoalTransactionRepository {
    suspend fun insertGoalTransaction(goalTransaction: GoalTransaction) : Long
    suspend fun updateGoalTransaction(goalTransaction: GoalTransaction) : Int
    suspend fun deleteGoalTransaction(goalTransaction: GoalTransaction)
    suspend fun deleteGoalTransaction(id: Long)
}

class GoalTransactionRepositoryImpl @Inject constructor(
    private val goalTransactionDao: GoalTransactionDao
) : GoalTransactionRepository {
    override suspend fun insertGoalTransaction(goalTransaction: GoalTransaction): Long {
        return goalTransactionDao.insertGoalTransaction(goalTransaction)
    }

    override suspend fun updateGoalTransaction(goalTransaction: GoalTransaction): Int {
        return goalTransactionDao.updateGoalTransaction(goalTransaction)
    }

    override suspend fun deleteGoalTransaction(goalTransaction: GoalTransaction) {
        return goalTransactionDao.deleteGoalTransaction(goalTransaction)
    }

    override suspend fun deleteGoalTransaction(id: Long) {
        return goalTransactionDao.deleteGoalTransaction(id)
    }
}