package com.example.money_manager_app.fragment.init_account

import androidx.lifecycle.viewModelScope
import com.example.money_manager_app.base.BaseViewModel
import com.example.money_manager_app.data.model.entity.Account
import com.example.money_manager_app.data.model.entity.Wallet
import com.example.money_manager_app.data.model.entity.enums.Currency
import com.example.money_manager_app.data.model.entity.enums.WalletType
import com.example.money_manager_app.data.repository.AccountRepository
import com.example.money_manager_app.data.repository.WalletRepository
import com.example.moneymanager.di.AppDispatchers
import com.example.moneymanager.di.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitAccountViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val accountRepository: AccountRepository,
    private val walletRepository: WalletRepository
) : BaseViewModel() {
    private var _name = ""
    private var _currency = Currency.USD
    private var _initAmount = 0.0

    fun setName(name: String) {
        _name = name
    }

    fun setCurrency(currency: Currency) {
        _currency = currency
    }

    fun setInitAmount(initAmount: Double) {
        _initAmount = initAmount
    }

    fun addAccount() {
        viewModelScope.launch(ioDispatcher) {
            if (_name.isNotEmpty()) {
                val accountId = accountRepository.insertAccount(
                    Account(
                        nameAccount = _name, currency = _currency
                    )
                )
                val walletId = walletRepository.insertWallet(
                    Wallet(
                        accountId = accountId,
                        amount = _initAmount,
                        typeWallet = WalletType.GENERAL,
                        iconId = "ic_general",
                        colorId = "color_general",
                        name = "General",
                        isExcluded = false
                    )
                )
            }
        }
    }
}

