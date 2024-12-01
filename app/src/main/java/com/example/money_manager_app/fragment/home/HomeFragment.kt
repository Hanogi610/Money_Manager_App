package com.example.money_manager_app.fragment.home

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.money_manager_app.R
import com.example.money_manager_app.adapter.TransactionAdapter
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.data.model.entity.enums.WalletType
import com.example.money_manager_app.databinding.FragmentHomeBinding
import com.example.money_manager_app.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    private lateinit var transactionAdapter: TransactionAdapter
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun getVM(): HomeViewModel {
        val vm: HomeViewModel by viewModels()
        return vm
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        val currencySymbol =
            getString(mainViewModel.currentAccount.value!!.account.currency.symbolRes)
        transactionAdapter = TransactionAdapter(
            requireContext(),
            currencySymbol,
            mainViewModel.currentAccount.value!!.wallets,
            mainViewModel.categories.value
        ) {
            // on transaction click
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.currentAccount.collect {
                    it?.let {
                        val balance =
                            it.wallets.filter { wallet -> wallet.walletType == WalletType.GENERAL && wallet.isExcluded == false }
                                .sumOf { wallet -> wallet.amount }
                        val currencySymbol = getString(it.account.currency.symbolRes)
                        binding.balanceAmount.text =
                            getString(R.string.money_amount, currencySymbol, balance)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getVM().transactions.collect {
                    transactionAdapter.submitList(it)
                }
            }
        }
    }
}