package com.example.money_manager_app.data.repository

import com.example.money_manager_app.data.dao.TransferDao
import com.example.money_manager_app.data.model.entity.Transfer
import javax.inject.Inject


interface TransferRepository {
    suspend fun insertTransfer(transfer : Transfer) : Long

}

class TransferRepositoryImpl @Inject constructor(
    private val transferDao: TransferDao
) : TransferRepository {
    override suspend fun insertTransfer(transfer: Transfer): Long {
        return transferDao.insertTransfer(transfer)
    }

}
