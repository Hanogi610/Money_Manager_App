package com.example.money_manager_app.activity

import androidx.lifecycle.viewModelScope
import com.example.money_manager_app.base.BaseViewModel
import com.example.money_manager_app.di.AppDispatchers
import com.example.money_manager_app.di.Dispatcher
import com.example.money_manager_app.model.entity.enums.Currency
import com.example.moneymanager.data.model.entity.Account
import com.example.moneymanager.data.model.entity.AccountWithWallet
import com.example.moneymanager.data.model.entity.Debt
import com.example.moneymanager.data.model.entity.Wallet
import com.example.moneymanager.data.model.entity.enums.WalletType
import com.example.moneymanager.data.repository.AccountRepository
import com.example.moneymanager.data.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddingAccount(
    val name: String,
    val currency: Currency,
    val initAmount: Double,
)

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

    private val _addingAccount = MutableStateFlow(AddingAccount("", Currency.USD, 0.0))
    val addingAccount: StateFlow<AddingAccount> get() = _addingAccount

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

    fun setAddingAccount(addingAccount: AddingAccount) {
        _addingAccount.value = addingAccount
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

    fun addAccount() {
        viewModelScope.launch(ioDispatcher) {
            val newAccount = addingAccount.value
            if (newAccount.name.isNotEmpty()) {
                val accountId = accountRepository.insertAccount(
                    Account(
                        nameAccount = newAccount.name,
                        currency = newAccount.currency
                    )
                )
                val walletId = walletRepository.insertWallet(
                    Wallet(
                        accountId = accountId,
                        amount = newAccount.initAmount,
                        typeWallet = WalletType.GENERAL,
                        iconId = "ic_general",
                        colorId = "color_general",
                        name = "General",
                        isExcluded = false
                    )
                )

                setCurrentAccount(
                    AccountWithWallet(
                        Account(
                            id = accountId,
                            nameAccount = newAccount.name,
                            currency = newAccount.currency
                        ),
                        listOf(
                            Wallet(
                                id = walletId,
                                accountId = accountId,
                                amount = newAccount.initAmount,
                                typeWallet = WalletType.GENERAL,
                                iconId = "ic_general",
                                colorId = "color_general",
                                name = "General",
                                isExcluded = false
                            )
                        )
                    )
                )

                _addingAccount.value = AddingAccount("", Currency.USD, 0.0)
            }
        }
    }

    fun setPasscode(passcode: String) {
        _passcode.value = passcode
    }
}