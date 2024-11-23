package com.example.money_manager_app.fragment.wallet.debt_detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.money_manager_app.base.BaseViewModel
import com.example.money_manager_app.data.model.entity.DebtDetail
import com.example.money_manager_app.data.repository.DebtRepository
import com.example.money_manager_app.di.AppDispatchers
import com.example.money_manager_app.di.Dispatcher
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

    fun getDebtDetails(debtId: Long) {
        viewModelScope.launch(ioDispatcher) {
            debtRepository.getDebtDetailsByDebtId(debtId).collect {
                Log.d(TAG, "getDebtDetails: $it")
                _debtInfo.value = it
            }
        }
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