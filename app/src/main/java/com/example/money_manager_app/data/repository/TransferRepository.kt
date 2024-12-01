package com.example.money_manager_app.data.repository

import com.example.money_manager_app.data.dao.TransferDao
import com.example.money_manager_app.data.model.entity.Transfer
import com.example.money_manager_app.data.model.entity.TransferWithCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface TransferRepository {
    suspend fun insertTransferDetail(transfer: Transfer): Long
    fun getTransferFromDayStartAndDayEnd(
        startDay: Long,
        endDay: Long,
        accountId: Long
    ): Flow<List<TransferWithCategory>>

    fun getTransferByAccountId(accountId: Long): List<TransferWithCategory>
    fun searchByDateAndAmountAndDesAndCategoryAndWallet(
        startDate: Long?,
        endDate: Long?,
        minAmount: Double?,
        maxAmount: Double?,
        description: String?,
        fromWallet: Long?
    ): List<TransferWithCategory>

    fun getAllTransfer(date: Long): Flow<List<TransferWithCategory>>
}


class TransferRepositoryImpl @Inject constructor(
    private val transferDao: TransferDao
) : TransferRepository {

    override suspend fun insertTransferDetail(transfer: Transfer): Long {
        return transferDao.insertTransfer(transfer)
    }

    override fun getTransferFromDayStartAndDayEnd(
        startDay: Long,
        endDay: Long,
        accountId: Long
    ): Flow<List<TransferWithCategory>> {
        return transferDao.getTransferFromDayStartAndDayEnd(startDay, endDay, accountId)
    }

    override fun getTransferByAccountId(accountId: Long): List<TransferWithCategory> {
        return transferDao.getTransfersByAccountId(accountId)
    }

    override fun searchByDateAndAmountAndDesAndCategoryAndWallet(
        startDate: Long?,
        endDate: Long?,
        minAmount: Double?,
        maxAmount: Double?,
        description: String?,
        fromWallet: Long?
    ): List<TransferWithCategory> {
        return transferDao.searchByDateAndAmountAndDesAndCategoryAndWallet(
            startDate, endDate, minAmount, maxAmount, description, fromWallet
        )
    }

    override fun getAllTransfer(date: Long): Flow<List<TransferWithCategory>> {
        return transferDao.getAllTransfer(date)
    }
}
