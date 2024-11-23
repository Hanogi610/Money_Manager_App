package com.example.money_manager_app.fragment.wallet

import androidx.lifecycle.viewModelScope
import com.example.money_manager_app.base.BaseViewModel
import com.example.money_manager_app.data.model.entity.DebtDetail
import com.example.money_manager_app.data.model.entity.Goal
import com.example.money_manager_app.data.model.entity.GoalDetail
import com.example.money_manager_app.data.model.entity.Wallet
import com.example.money_manager_app.data.repository.DebtRepository
import com.example.money_manager_app.data.repository.GoalRepository
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
class WalletViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val walletRepository: WalletRepository,
    private val debtRepository: DebtRepository,
    private val goalRepository: GoalRepository
) : BaseViewModel() {

    private val _wallets = MutableStateFlow<List<Wallet>>(emptyList())
    val wallets: StateFlow<List<Wallet>> get() = _wallets

    private val _debts = MutableStateFlow<List<DebtDetail>>(emptyList())
    val debts: StateFlow<List<DebtDetail>> get() = _debts

    private val _goals = MutableStateFlow<List<GoalDetail>>(emptyList())
    val goals: StateFlow<List<GoalDetail>> get() = _goals

    fun getWallets(accountId: Long) {
        viewModelScope.launch {
            walletRepository.getWalletsByUserId(accountId).collect {
                _wallets.value = it
            }
        }
    }

    fun getDebts(accountId: Long) {
        viewModelScope.launch {
            debtRepository.getDebtsByAccountId(accountId).collect {
                _debts.value = it
            }
        }
    }

    fun getGoals(accountId: Long) {
        viewModelScope.launch {
            goalRepository.getGoalsByAccountId(accountId).collect {
                _goals.value = it
            }
        }
    }
}