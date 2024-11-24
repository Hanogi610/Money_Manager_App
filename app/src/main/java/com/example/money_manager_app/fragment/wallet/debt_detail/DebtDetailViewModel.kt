package com.example.money_manager_app.fragment.wallet.debt_detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.money_manager_app.base.BaseViewModel
import com.example.money_manager_app.data.model.TransactionListItem
import com.example.money_manager_app.data.model.entity.DebtDetail
import com.example.money_manager_app.data.model.entity.enums.DebtActionType
import com.example.money_manager_app.data.repository.DebtRepository
import com.example.money_manager_app.di.AppDispatchers
import com.example.money_manager_app.di.Dispatcher
import com.example.money_manager_app.utils.groupTransactionsByDate
import com.example.money_manager_app.utils.toFormattedDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DebtDetailViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val debtRepository: DebtRepository
) : BaseViewModel() {
    private val _debtInfo = MutableStateFlow<DebtDetail?>(null)
    val debtInfo: StateFlow<DebtDetail?> get() = _debtInfo

    private val _debtDetailItem = MutableStateFlow<DebtDetailItem?>(null)
    val debtDetailItem: StateFlow<DebtDetailItem?> get() = _debtDetailItem

    fun getDebtDetails(debtId: Long) {
        viewModelScope.launch(ioDispatcher) {
            debtRepository.getDebtDetailsByDebtId(debtId).collect {
                Log.d(TAG, "getDebtDetails: $it")
                _debtInfo.value = it
                it.let {
                    _debtDetailItem.value = convertDebtDetailToDebtDetailItem(it)
                }
            }
        }
    }

    private fun convertDebtDetailToDebtDetailItem(debtDetail: DebtDetail): DebtDetailItem {
        val paidAmount = debtDetail.transactions.filter { it.action == DebtActionType.REPAYMENT }
            .sumOf { it.amount }
        val remainingAmount = debtDetail.debt.amount - paidAmount
        val date = debtDetail.debt.date.toFormattedDateString()
        val walletId = debtDetail.debt.walletId
        val groupedTransactions = debtDetail.transactions.groupTransactionsByDate()
        return DebtDetailItem(
            title = debtDetail.debt.name,
            description = debtDetail.debt.description,
            totalAmount = debtDetail.debt.amount,
            paidAmount = paidAmount,
            remainingAmount = remainingAmount,
            date = date,
            walletId = walletId,
            transactions = groupedTransactions
        )
    }

    fun deleteDebt(debtId: Long) {
        viewModelScope.launch(ioDispatcher) {
            debtRepository.deleteDebt(debtId)
        }
    }

    companion object {
        private const val TAG = "DebtDetailViewModel"
    }
}

data class DebtDetailItem(
    val title: String,
    val description: String,
    val totalAmount: Double,
    val paidAmount: Double,
    val remainingAmount: Double,
    val date: String,
    val walletId: Long,
    val transactions: List<TransactionListItem>,
    val progress: Int = ((paidAmount / totalAmount) * 100).toInt()
)