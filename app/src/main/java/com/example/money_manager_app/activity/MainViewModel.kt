package com.example.money_manager_app.activity

import androidx.lifecycle.viewModelScope
import com.example.money_manager_app.base.BaseViewModel
import com.example.money_manager_app.data.model.entity.Account
import com.example.money_manager_app.data.model.entity.AccountWithWallet
import com.example.money_manager_app.data.model.entity.Debt
import com.example.money_manager_app.data.model.entity.Wallet
import com.example.money_manager_app.data.model.entity.enums.Currency
import com.example.money_manager_app.data.model.entity.enums.WalletType
import com.example.money_manager_app.data.repository.AccountRepository
import com.example.money_manager_app.data.repository.WalletRepository
import com.example.moneymanager.di.AppDispatchers
import com.example.moneymanager.di.Dispatcher
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



    private val _passcode = MutableStateFlow("")
    val passcode: StateFlow<String> get() = _passcode

    private val _currentWallet = MutableStateFlow<Wallet?>(null)
    val currentWallet: StateFlow<Wallet?> get() = _currentWallet

    private val _currentDebt = MutableStateFlow<Debt?>(null)
    val currentDebt: StateFlow<Debt?> get() = _currentDebt

    fun setCurrentWallet(wallet: Wallet) {
        _currentWallet.value = wallet
    }

    fun setCurrentDebt(debt: Debt?) {
        _currentDebt.value = debt
    }



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

    fun setPasscode(passcode: String) {
        _passcode.value = passcode
    }
}
