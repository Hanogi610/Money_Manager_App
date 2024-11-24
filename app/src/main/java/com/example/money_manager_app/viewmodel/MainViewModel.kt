package com.example.money_manager_app.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.money_manager_app.base.BaseViewModel
import com.example.money_manager_app.data.model.entity.AccountWithWallet
import com.example.money_manager_app.data.repository.AccountRepository
import com.example.money_manager_app.data.repository.WalletRepository
import com.example.money_manager_app.di.AppDispatchers
import com.example.money_manager_app.di.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val accountRepository: AccountRepository,
    private val walletRepository: WalletRepository
): BaseViewModel() {
    private val _accounts : MutableStateFlow<List<AccountWithWallet>> = MutableStateFlow(emptyList())
    val accounts : StateFlow<List<AccountWithWallet>> get() = _accounts

    private val _currentAccount : MutableStateFlow<AccountWithWallet?> = MutableStateFlow(null)
    val currentAccount : StateFlow<AccountWithWallet?> get() = _currentAccount

    fun setCurrentAccount(account: AccountWithWallet) {
        _currentAccount.value = account
    }

    fun getAccount() {
        viewModelScope.launch {
            accountRepository.getAccount().collect {
                _accounts.value = it
            }
        }
    }
}