package com.example.money_manager_app.data.repository

import com.example.money_manager_app.data.dao.TransferDao
import com.example.money_manager_app.data.model.TransferDetail
import com.example.money_manager_app.data.model.entity.Transfer
import com.example.money_manager_app.data.model.entity.TransferCategoryCrossRef
import com.example.money_manager_app.data.model.entity.TransferWithCategory
import com.example.money_manager_app.data.model.toTransferDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface TransferRepository {
    suspend fun insertTransferDetail(transferDetail: TransferDetail): Long
    fun getTransferFromDayStartAndDayEnd(startDay: Long, endDay: Long, accountId: Long): Flow<List<TransferDetail>>
    fun getTransferByAccountId(accountId: Long): List<TransferDetail>
    fun searchByDateAndAmountAndDesAndCategoryAndWallet(
        startDate: Long?,
        endDate: Long?,
        minAmount: Double?,
        maxAmount: Double?,
        description: String?,
        fromWallet: Long?
    ): List<TransferDetail>
    fun getAllTransfer(date: Long): Flow<List<TransferDetail>>
}


class TransferRepositoryImpl @Inject constructor(
    private val transferDao: TransferDao
) : TransferRepository {

    override suspend fun insertTransferDetail(transferDetail: TransferDetail): Long {
        // Convert TransferDetail to Transfer entity
        val transfer = Transfer(
            id = transferDetail.id,
            walletId = transferDetail.walletId,
            toWalletId = transferDetail.toWalletId,
            amount = transferDetail.amount,
            name = transferDetail.name,
            fee = transferDetail.fee,
            description = transferDetail.description,
            accountId = transferDetail.accountId,
            linkImg = transferDetail.linkImg,
            date = transferDetail.date,
            time = transferDetail.time,
            typeOfExpenditure = transferDetail.typeOfExpenditure,
            iconId = transferDetail.iconId
        )
        val transferId = transferDao.insertTransfer(transfer)

        // Insert associated categories
        val transferCategoryCrossRefs = transferDetail.categories.map { category ->
            TransferCategoryCrossRef(transferId, category.id)
        }
        transferDao.insertTransferCategoryCrossRefs(transferCategoryCrossRefs)
        return transferId
    }

    override fun getTransferFromDayStartAndDayEnd(startDay: Long, endDay: Long, accountId: Long): Flow<List<TransferDetail>> {
        return transferDao.getTransferFromDayStartAndDayEnd(startDay, endDay, accountId)
            .map { transferWithCategories ->
                transferWithCategories.map { it.toTransferDetail() }
            }
    }

    override fun getTransferByAccountId(accountId: Long): List<TransferDetail> {
        return transferDao.getTransfersByAccountId(accountId).map { it.toTransferDetail() }
    }

    override fun searchByDateAndAmountAndDesAndCategoryAndWallet(
        startDate: Long?,
        endDate: Long?,
        minAmount: Double?,
        maxAmount: Double?,
        description: String?,
        fromWallet: Long?
    ): List<TransferDetail> {
        return transferDao.searchByDateAndAmountAndDesAndCategoryAndWallet(
            startDate, endDate, minAmount, maxAmount, description, fromWallet
        ).map { it.toTransferDetail() }
    }

    override fun getAllTransfer(date: Long): Flow<List<TransferDetail>> {
        return transferDao.getAllTransfer(date)
            .map { transferWithCategories ->
                transferWithCategories.map { it.toTransferDetail() }
            }
    }
}
