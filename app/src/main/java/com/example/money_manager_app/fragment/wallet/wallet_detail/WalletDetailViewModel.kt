package com.example.money_manager_app.fragment.wallet.wallet_detail

import com.example.money_manager_app.base.BaseViewModel
import com.example.money_manager_app.data.repository.DebtRepository
import com.example.money_manager_app.data.repository.TransferRepository
import com.example.money_manager_app.data.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalletDetailViewModel @Inject constructor(
    private val debtRepository: DebtRepository,
    private val walletRepository: WalletRepository,
    private val transferRepository: TransferRepository
): BaseViewModel() {

}