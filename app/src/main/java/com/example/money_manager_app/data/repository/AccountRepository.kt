package com.example.money_manager_app.data.repository

import com.example.money_manager_app.data.dao.AccountDao
import com.example.money_manager_app.data.model.entity.Account
import com.example.money_manager_app.data.model.entity.AccountWithWallet
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