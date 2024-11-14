package com.example.moneymanager.data.repository

import com.example.moneymanager.data.dao.AccountDao
import com.example.moneymanager.data.model.entity.Account
import com.example.moneymanager.data.model.entity.AccountWithWallet
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AccountRepository {
    fun getAccount() : Flow<List<AccountWithWallet>>
    suspend fun insertAccount(account: Account) : Long
}

class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao
) : AccountRepository {

    override fun getAccount() = accountDao.getAccount()

    override suspend fun insertAccount(account: Account): Long {
        return accountDao.insertAccount(account)
    }

}