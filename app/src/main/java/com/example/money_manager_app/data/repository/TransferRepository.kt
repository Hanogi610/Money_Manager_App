package com.example.moneymanager.data.repository

import com.example.moneymanager.data.dao.TransferDao
import com.example.moneymanager.data.model.entity.Transfer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface TransferRepository {
    suspend fun insertTransfer(transfer : Transfer) : Long
    fun getAllTransfer(date : Long) : Flow<List<Transfer>>

}

class TransferRepositoryImpl @Inject constructor(
    private val transferDao: TransferDao
) : TransferRepository {
    override suspend fun insertTransfer(transfer: Transfer): Long {
        return transferDao.insertTransfer(transfer)
    }

    override fun getAllTransfer(date : Long): Flow<List<Transfer>> {
        return transferDao.getAllTransfer(date)
    }

}
