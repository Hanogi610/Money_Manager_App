package com.example.money_manager_app.data.repository

import com.example.money_manager_app.data.dao.TransferDao
import com.example.money_manager_app.data.model.entity.Transfer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface TransferRepository {
    suspend fun insertTransfer(transfer : Transfer) : Long
    fun getTransferFromDayStartAndDayEnd(startDay : Long, endDay : Long, accountId : Long) : Flow<List<Transfer>>
    fun searchByDateAndAmountAndDesAndCategoryAndWallet(
        startDate: Long?,
        endDate: Long?,
        minAmount: Double?,
        maxAmount: Double?,
        description: String?, categoryType: Int?, fromWallet: Long?): List<Transfer>
    fun getAllTransfer(date : Long) : Flow<List<Transfer>>

}

class TransferRepositoryImpl @Inject constructor(
    private val transferDao: TransferDao
) : TransferRepository {
    override suspend fun insertTransfer(transfer: Transfer): Long {
        return transferDao.insertTransfer(transfer)
    }

    override fun searchByDateAndAmountAndDesAndCategoryAndWallet(
        startDate: Long?,
        endDate: Long?,
        minAmount: Double?,
        maxAmount: Double?,
        description: String?,
        categoryType: Int?,
        fromWallet: Long?
    ): List<Transfer> {
        return transferDao.searchByDateAndAmountAndDesAndCategoryAndWallet(
            startDate,
            endDate,
            minAmount,
            maxAmount,
            description,
            categoryType,
            fromWallet
        )
    }

    override fun getTransferFromDayStartAndDayEnd(startDay: Long, endDay: Long, accountId: Long): Flow<List<Transfer>> {
        return transferDao.getTransferFromDayStartAndDayEnd(startDay, endDay, accountId)
    }

    override fun getAllTransfer(date : Long): Flow<List<Transfer>> {
        return transferDao.getAllTransfer(date)
    }

}
