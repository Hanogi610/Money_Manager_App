package com.example.money_manager_app.data.repository

import com.example.money_manager_app.data.dao.DebtTransactionDao
import com.example.money_manager_app.data.model.entity.DebtTransaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DebtTransactionRepository {
    suspend fun insertDebtTransaction(debtTransaction: DebtTransaction) : Long
    suspend fun editDebtTransaction(debtTransaction: DebtTransaction)
    fun getDebtTransactionsByDebtId(debtId: Long) : Flow<List<DebtTransaction>>
    fun getDebtTransactionsByAccountId(accountId: Long) : Flow<List<DebtTransaction>>
    fun getDebtTransactionFromDayStartAndDayEnd(
        accountId: Long,
        startDay: Long,
        endDay: Long
    ): Flow<List<DebtTransaction>>
    fun getDebtTransactionsByDateAndAccountId(
        date: Long,
        accountId: Long
    ): Flow<List<DebtTransaction>>
    suspend fun deleteDebtTransaction(debtTransactionId: Long)
    suspend fun deleteDebtTransaction(debtTransaction: DebtTransaction)
}

class DebtTransactionRepositoryImpl @Inject constructor(
    private val debtTransactionDao: DebtTransactionDao
) : DebtTransactionRepository {
    override suspend fun insertDebtTransaction(debtTransaction: DebtTransaction): Long {
        return debtTransactionDao.insertDebtTransaction(debtTransaction)
    }

    override fun getDebtTransactionsByDateAndAccountId(
        date: Long,
        accountId: Long
    ): Flow<List<DebtTransaction>> {
        return debtTransactionDao.getDebtTransactionsByDateAndAccountId(date, accountId)
    }


    override suspend fun editDebtTransaction(debtTransaction: DebtTransaction) {
        debtTransactionDao.editDebtTransaction(debtTransaction)
    }

    override fun getDebtTransactionsByDebtId(debtId: Long): Flow<List<DebtTransaction>> {
        return debtTransactionDao.getDebtTransactionsByDebtId(debtId)
    }

    override fun getDebtTransactionsByAccountId(accountId: Long): Flow<List<DebtTransaction>> {
        return debtTransactionDao.getDebtTransactionsByAccountId(accountId)
    }

    override fun getDebtTransactionFromDayStartAndDayEnd(
        accountId: Long,
        startDay: Long,
        endDay: Long
    ): Flow<List<DebtTransaction>> {
        return debtTransactionDao.getDebtTransactionFromDayStartAndDayEnd(accountId, startDay, endDay)
    }

    override suspend fun deleteDebtTransaction(debtTransactionId: Long) {
        debtTransactionDao.deleteDebtTransaction(debtTransactionId)
    }

    override suspend fun deleteDebtTransaction(debtTransaction: DebtTransaction) {
        debtTransactionDao.deleteDebtTransaction(debtTransaction)
    }
}