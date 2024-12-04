package com.example.money_manager_app.fragment.wallet.wallet_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.money_manager_app.R
import com.example.money_manager_app.adapter.TransactionAdapter
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.data.model.entity.Wallet
import com.example.money_manager_app.databinding.FragmentWalletDetailBinding
import com.example.money_manager_app.utils.setOnSafeClickListener
import com.example.money_manager_app.utils.toFormattedDateString
import com.example.money_manager_app.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WalletDetailFragment :
    BaseFragment<FragmentWalletDetailBinding, WalletDetailViewModel>(R.layout.fragment_wallet_detail) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var wallet: Wallet? = null
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var currentCurrencySymbol: String

    override fun getVM(): WalletDetailViewModel {
        val viewModel: WalletDetailViewModel by viewModels()
        return viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        arguments?.let {
            wallet = it.getParcelable("wallet")
            wallet?.let {
                getVM().getWalletDetailItem(it.id)
                getVM().getTransactionByAccountIdAndWalletId(it.accountId, it.id)
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        currentCurrencySymbol =
            getString(mainViewModel.currentAccount.value!!.account.currency.symbolRes)
        transactionAdapter = TransactionAdapter(
            requireContext(),
            currentCurrencySymbol,
            mainViewModel.currentAccount.value!!.wallets,
            listOf()
        )
    }

    override fun bindingStateView() {
        super.bindingStateView()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getVM().walletDetailItemState.collect { walletDetailItem ->
                    walletDetailItem?.let {
                        binding.apply {
                            walletIconImageView.setImageResource(it.iconId)
                            walletIconImageView.setBackgroundResource(it.colorId)
                            nameLabel.text = it.name
                            transactionAdapter.submitList(it.transactions)
                            actionButton.setBackgroundResource(it.colorId)
                            fab.setBackgroundColor(it.colorId)
                            when (it) {
                                is WalletDetailItem.CreditItem -> {
                                    generalLayout.visibility = View.GONE
                                    creditLayout.visibility = View.VISIBLE
                                    creditLimitLabel.text = getString(
                                        R.string.money_amount, currentCurrencySymbol, it.creditLimit
                                    )
                                    expenseCredit.text = getString(
                                        R.string.money_amount, currentCurrencySymbol, it.expense
                                    )
                                    dueDate.text = it.dueDate.toFormattedDateString()
                                    statementDate.text = it.statementDate.toFormattedDateString()
                                    actionButton.text = getString(R.string.make_payment)
                                    balanceLabel.text = getString(
                                        R.string.negative_money_amount,
                                        currentCurrencySymbol,
                                        it.creditLimit - it.availableCredit
                                    )
                                }

                                is WalletDetailItem.GeneralItem -> {
                                    initialBalance.text = getString(
                                        R.string.money_amount,
                                        currentCurrencySymbol,
                                        it.initialBalance
                                    )
                                    income.text = getString(R.string.transactions_format, it.income)
                                    expenseGeneral.text =
                                        getString(R.string.transactions_format, it.expense)
                                    transfer.text =
                                        getString(R.string.transactions_format, it.transfer)
                                    actionButton.text = getString(R.string.adjust_balance)
                                    balanceLabel.text = getString(
                                        R.string.money_amount,
                                        currentCurrencySymbol,
                                        it.currentBalance
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun setOnClick() {
        super.setOnClick()

        binding.fab.setOnSafeClickListener {
            val walletToSent = getVM().walletDetailItemState.value?.wallet
            appNavigation.openMainScreenToAddFragmentScreen(Bundle().apply {
                putParcelable("wallet", walletToSent)
            })
        }
        binding.actionButton.setOnSafeClickListener {
            getVM().walletDetailItemState.value?.let {
                when (it) {
                    is WalletDetailItem.CreditItem -> {
                        appNavigation.openMainScreenToAddFragmentScreen(Bundle().apply {
                            putParcelable("wallet", it.wallet)
                        })
                    }

                    is WalletDetailItem.GeneralItem -> {
                        appNavigation.openMainScreenToAddWalletScreen(Bundle().apply {
                            putParcelable("wallet", it.wallet)
                        })
                    }
                }
            }
        }
    }
}