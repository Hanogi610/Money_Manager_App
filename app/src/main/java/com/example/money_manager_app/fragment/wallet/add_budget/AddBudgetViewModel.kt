package com.example.money_manager_app.fragment.wallet.add_budget

import com.example.money_manager_app.base.BaseViewModel
import com.example.money_manager_app.data.repository.BudgetRepository
import com.example.money_manager_app.di.AppDispatchers
import com.example.money_manager_app.di.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class AddBudgetViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val budgetRepository: BudgetRepository
): BaseViewModel() {
    // TODO: Implement the ViewModel
}