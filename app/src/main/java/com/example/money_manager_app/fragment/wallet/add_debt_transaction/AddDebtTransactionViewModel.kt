package com.example.money_manager_app.fragment.wallet.add_debt_transaction

import androidx.lifecycle.viewModelScope
import com.example.money_manager_app.base.BaseViewModel
import com.example.money_manager_app.data.model.entity.Debt
import com.example.money_manager_app.data.model.entity.DebtTransaction
import com.example.money_manager_app.data.model.entity.enums.DebtType
import com.example.money_manager_app.data.repository.DebtRepository
import com.example.money_manager_app.data.repository.DebtTransactionRepository
import com.example.money_manager_app.di.AppDispatchers
import com.example.money_manager_app.di.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDebtTransactionViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val debtTransactionRepository: DebtTransactionRepository,
    private val debtRepository: DebtRepository
) : BaseViewModel() {

    fun addDebtTransaction(debtTransaction: DebtTransaction): Long {
        return if (debtTransaction.amount > 0) {
            var debtId: Long = -1
            viewModelScope.launch(ioDispatcher) {
                debtId = debtTransactionRepository.insertDebtTransaction(debtTransaction)
            }
            debtId
        } else {
            -1L
        }
    }

    fun getDebtById(debtId: Long) : Debt {
        var debt: Debt = Debt(0, 0, "", 0.0, 0, DebtType.PAYABLE , 0, 0, "", 0)
        viewModelScope.launch(ioDispatcher) {
            debt = debtRepository.getDebt(debtId)
        }
        return debt

    }

    fun updateDebtTransaction(debtTransaction: DebtTransaction){
        (debtTransaction.amount > 0).let {
            viewModelScope.launch(ioDispatcher) {
                debtTransactionRepository.editDebtTransaction(debtTransaction)
            }
        }
    }

    fun deleteDebtTransaction(debtId: Long) {
        viewModelScope.launch(ioDispatcher) {
            debtTransactionRepository.deleteDebtTransaction(debtId)
        }
    }
}