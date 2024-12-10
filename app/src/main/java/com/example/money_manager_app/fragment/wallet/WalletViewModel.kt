package com.example.money_manager_app.fragment.wallet

import androidx.lifecycle.viewModelScope
import com.example.money_manager_app.base.BaseViewModel
import com.example.money_manager_app.data.model.entity.Budget
import com.example.money_manager_app.data.model.entity.BudgetWithCategory
import com.example.money_manager_app.data.model.entity.DebtDetail
import com.example.money_manager_app.data.model.entity.GoalDetail
import com.example.money_manager_app.data.model.entity.Wallet
import com.example.money_manager_app.data.repository.BudgetRepository
import com.example.money_manager_app.data.repository.DebtRepository
import com.example.money_manager_app.data.repository.GoalRepository
import com.example.money_manager_app.data.repository.TransferRepository
import com.example.money_manager_app.data.repository.WalletRepository
import com.example.money_manager_app.di.AppDispatchers
import com.example.money_manager_app.di.Dispatcher
import com.example.money_manager_app.utils.toDateTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val walletRepository: WalletRepository,
    private val debtRepository: DebtRepository,
    private val goalRepository: GoalRepository,
    private val budgetRepository: BudgetRepository,
    private val tranferRepository: TransferRepository
) : BaseViewModel() {

    private val _wallets = MutableStateFlow<List<Wallet>>(emptyList())
    val wallets: StateFlow<List<Wallet>> get() = _wallets

    private val _debts = MutableStateFlow<List<DebtDetail>>(emptyList())
    val debts: StateFlow<List<DebtDetail>> get() = _debts

    private val _goals = MutableStateFlow<List<GoalDetail>>(emptyList())
    val goals: StateFlow<List<GoalDetail>> get() = _goals

    private val _budgetsWithCategory = MutableStateFlow<List<BudgetWithCategory>>(emptyList())
    val budgetsWithCategory: StateFlow<List<BudgetWithCategory>> get() = _budgetsWithCategory

    private val _budgets = MutableStateFlow<List<Budget>>(emptyList())
    val budgets: StateFlow<List<Budget>> get() = _budgets

    fun setBudgets(budgets: List<Budget>) {
        _budgets.value = budgets
    }
    fun getBudgets(accountId: Long) {
        viewModelScope.launch {
            budgetRepository.getBudgetsByAccountId(accountId).collect {
                _budgetsWithCategory.value = it
            }
        }
    }

    fun checkBudgetNeedUpdate(accountId: Long) {
        viewModelScope.launch(ioDispatcher) {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val calendarToday = Calendar.getInstance()
            val todayDate = dateFormat.format(calendarToday.time).toDateTimestamp()
            val budgets = budgetRepository.getBudgetsByAccountIdAndDate(accountId, todayDate)
            for (item in budgets) {
                var spent = 0.0
                for(category in item.categories) {
                    val transactions = tranferRepository.getTransferWithCategoryByAccountId(
                        item.budget.accountId,
                        category.id,
                        item.budget.startDate,
                        item.budget.endDate
                    )

                    for (transaction in transactions) {
                        spent += transaction.amount
                    }
                }
                budgetRepository.editBudget(item.budget.copy(spent = spent.toInt()))
                spent = 0.0
            }
        }
    }


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