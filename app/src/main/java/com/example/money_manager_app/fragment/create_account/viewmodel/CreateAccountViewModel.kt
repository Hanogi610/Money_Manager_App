package com.example.money_manager_app.fragment.create_account.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.money_manager_app.base.BaseViewModel
import com.example.money_manager_app.data.model.entity.Account
import com.example.money_manager_app.data.model.entity.Wallet
import com.example.money_manager_app.data.model.entity.enums.Currency
import com.example.money_manager_app.data.model.entity.enums.WalletType
import com.example.money_manager_app.data.repository.AccountRepository
import com.example.money_manager_app.data.repository.WalletRepository
import com.example.money_manager_app.di.AppDispatchers
import com.example.money_manager_app.di.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val accountRepository: AccountRepository,
    private val walletRepository: WalletRepository
): BaseViewModel() {
    private var _name = ""
    private var _currency = Currency.USD
    private var _initAmount = 0.0

    private val _isEnterName = MutableLiveData<Boolean>()
    val isEnterName: LiveData<Boolean> get() = _isEnterName

    private val _currentPage = MutableLiveData(0)
    val currentPage get() = _currentPage

    fun setCurrentPage(page: Int) {
        _currentPage.value = page
    }

    fun backPage() {
        if (_currentPage.value == 0) return
        _currentPage.value = _currentPage.value?.minus(1)
    }

    fun setName(name: String) {
        _name = name
        _currentPage.value = 1
    }

    fun setCurrency(currency: Currency) {
        _currency = currency
        _currentPage.value = 2
    }

    fun getCurrency(): Currency {
        return _currency
    }

    fun setInitAmount(initAmount: Double) {
        _initAmount = initAmount
        _currentPage.value = 3
    }

    fun checkEnterName(name: String) {
        _isEnterName.value = name.isNotEmpty()
    }

    fun addAccount() {
        viewModelScope.launch(ioDispatcher) {
            if (_name.isNotEmpty()) {
                val accountId = accountRepository.insertAccount(
                    Account(
                        nameAccount = _name, currency = _currency
                    )
                )
                walletRepository.insertWallet(
                    Wallet(
                        accountId = accountId,
                        amount = _initAmount,
                        walletType = WalletType.GENERAL,
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
