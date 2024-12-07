package com.example.money_manager_app.fragment.transaction.view

import androidx.fragment.app.activityViewModels
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.databinding.FragmentTransactionBinding
import com.example.money_manager_app.fragment.transaction.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionFragment : BaseFragment<FragmentTransactionBinding, TransactionViewModel>(R.layout.fragment_transaction) {

    override fun getVM(): TransactionViewModel {
        val viewModel : TransactionViewModel by activityViewModels()
        return viewModel
    }
}