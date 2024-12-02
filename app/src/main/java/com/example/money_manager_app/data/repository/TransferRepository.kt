    package com.example.money_manager_app.data.repository

    import com.example.money_manager_app.data.dao.TransferDao
    import com.example.money_manager_app.data.model.entity.Category
    import com.example.money_manager_app.data.model.entity.Transfer
    import kotlinx.coroutines.flow.Flow
    import javax.inject.Inject

    interface TransferRepository {
        suspend fun insertTransferDetail(transfer: Transfer): Long
        fun getTransferFromDayStartAndDayEnd(
            startDay: Long,
            endDay: Long,
            accountId: Long
        ): Flow<List<Transfer>>
        fun getTransferByAccountId(accountId: Long): Flow<List<Transfer>>
        fun searchByDateAndAmountAndDesAndCategoryAndWallet(
            startDate: Long?,
            endDate: Long?,
            minAmount: Double?,
            maxAmount: Double?,
            description: String?,
            fromWallet: Long?,
            categoryId: Long?
        ): List<Transfer>

        fun getAllTransfer(date: Long): Flow<List<Transfer>>
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
        ): Flow<List<Transfer>> {
            return transferDao.getTransferFromDayStartAndDayEnd(startDay, endDay, accountId)
        }

        override fun getTransferByAccountId(accountId: Long): Flow<List<Transfer>> {
            return transferDao.getTransfersByAccountId(accountId)
        }

        override fun searchByDateAndAmountAndDesAndCategoryAndWallet(
            startDate: Long?,
            endDate: Long?,
            minAmount: Double?,
            maxAmount: Double?,
            description: String?,
            fromWallet: Long?,
            categoryId: Long?
        ): List<Transfer> {
            return transferDao.searchByDateAndAmountAndDesAndCategoryAndWallet(
                startDate, endDate, minAmount, maxAmount, description, fromWallet
            )
        }

        override fun getAllTransfer(date: Long): Flow<List<Transfer>> {
            return transferDao.getAllTransfer(date)
        }
    }
