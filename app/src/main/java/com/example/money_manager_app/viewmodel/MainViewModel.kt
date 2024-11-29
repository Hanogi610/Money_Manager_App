package com.example.money_manager_app.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.money_manager_app.base.BaseViewModel
import com.example.money_manager_app.data.model.entity.Account
import com.example.money_manager_app.data.model.entity.AccountWithWallet
import com.example.money_manager_app.data.model.entity.Wallet
import com.example.money_manager_app.data.model.entity.enums.WalletType
import com.example.money_manager_app.data.repository.AccountRepository
import com.example.money_manager_app.data.repository.WalletRepository
import com.example.money_manager_app.di.AppDispatchers
import com.example.money_manager_app.di.Dispatcher
import com.example.money_manager_app.pref.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val appPreferences: AppPreferences,
    private val accountRepository: AccountRepository,
    private val walletRepository: WalletRepository
) : BaseViewModel() {
    private val _accounts: MutableStateFlow<List<AccountWithWallet>> = MutableStateFlow(emptyList())
    val accounts: StateFlow<List<AccountWithWallet>> get() = _accounts

    private val _currentAccount: MutableStateFlow<AccountWithWallet?> = MutableStateFlow(null)
    val currentAccount: StateFlow<AccountWithWallet?> get() = _currentAccount

    init {
        fetchAccountsAndSetCurrentAccount()
    }

    fun getAccount() {
        viewModelScope.launch {
            accountRepository.getAccount().collect {
                _accounts.value = it
            }
        }
    }

    fun setCurrentAccount(account: AccountWithWallet) {
        _currentAccount.value = account
        appPreferences.setCurrentAccount(account.account.id)
    }

    fun insertAccount(account: Account, initAmount: Double) {
        viewModelScope.launch(ioDispatcher) {
            if (account.nameAccount.isNotEmpty()) {
                val accountId = accountRepository.insertAccount(account)
                walletRepository.insertWallet(
                    Wallet(
                        accountId = accountId,
                        amount = initAmount,
                        walletType = WalletType.GENERAL,
                        name = "General",
                        isExcluded = false
                    )
                )
                // After inserting, fetch the account and update the flow
                fetchCurrentAccountIfNeeded(accountId)
            }
        }
    }

    private fun fetchAccountsAndSetCurrentAccount() {
        viewModelScope.launch(ioDispatcher) {
            accountRepository.getAccount().collect { accountList ->
                Log.d(
                    "hoangph",
                    "fetchAccountsAndSetCurrentAccount() called with: accountList = $accountList"
                )
                _accounts.value = accountList

                // Set the current account only if it is not already set
                if (_currentAccount.value == null && accountList.isNotEmpty()) {
                    val savedAccountId = appPreferences.getCurrentAccount()
                    val current = accountList.find { it.account.id == savedAccountId } ?: accountList.first()
                    _currentAccount.value = current
                }
            }
        }
    }

    private fun fetchCurrentAccountIfNeeded(accountId: Long) {
        viewModelScope.launch(ioDispatcher) {
            // Check if current account is already set
//            if (_currentAccount.value == null) {
                accountRepository.getAccountById(accountId).collect { fetchedAccount ->
//                    _currentAccount.value = fetchedAccount
                    setCurrentAccount(fetchedAccount)
                }
//            }
        }
    }
}
