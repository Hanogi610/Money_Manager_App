package com.example.money_manager_app.fragment.wallet.debt_detail

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_manager_app.R
import com.example.money_manager_app.activity.MainViewModel
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.data.model.entity.Debt
import com.example.money_manager_app.data.model.entity.DebtTransaction
import com.example.money_manager_app.data.model.entity.enums.DebtActionType
import com.example.money_manager_app.databinding.FragmentDebtDetailBinding
import com.example.money_manager_app.fragment.wallet.adapter.DebtTransactionAdapter
import com.example.money_manager_app.navigation.AppNavigation
import com.example.money_manager_app.utils.setOnSafeClickListener
import com.example.money_manager_app.utils.toFormattedDateString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DebtDetailFragment :
    BaseFragment<FragmentDebtDetailBinding, DebtDetailViewModel>(R.layout.fragment_debt_detail) {

    private var debt: Debt? = null
    override fun getVM(): DebtDetailViewModel {
        val vm: DebtDetailViewModel by viewModels()
        return vm
    }

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var debtTransactionAdapter: DebtTransactionAdapter

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        arguments?.let {
            debt = it.getParcelable<Debt>("debt")
            getVM().getDebtDetails(debt!!.id)
        }

    }

    override fun initToolbar() {
        super.initToolbar()
        binding.backButton.setOnSafeClickListener {
            appNavigation.navigateUp()
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val currentCurrencySymbol =
            getString(mainViewModel.currentAccount.value!!.account.currency.symbolRes)
        debtTransactionAdapter = DebtTransactionAdapter(
            requireContext(), currentCurrencySymbol, mainViewModel.currentAccount.value!!.wallets
        )
        binding.debtTransactionRv.adapter = debtTransactionAdapter
        binding.debtTransactionRv.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun bindingStateView() {
        super.bindingStateView()

        val currentCurrencySymbol =
            getString(mainViewModel.currentAccount.value!!.account.currency.symbolRes)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getVM().debtInfo.collect {
                    it?.let {
                        binding.apply {
                            nameLabel.text = it.debt.name
                            descriptionLabel.text = it.debt.description
                            val paidAmount =
                                it.transactions.filter { action -> action.action == DebtActionType.REPAYMENT }
                                    .sumOf { transaction -> transaction.amount }
                            spentLabel.text =
                                getString(R.string.money_amount, currentCurrencySymbol, paidAmount)
                            remainLabel.text = getString(
                                R.string.money_amount,
                                currentCurrencySymbol,
                                it.debt.amount - paidAmount
                            )
                            progressBar.progress =
                                ((paidAmount.toFloat() / it.debt.amount) * 100).toInt()
                            amountLabel.text = getString(
                                R.string.money_amount, currentCurrencySymbol, it.debt.amount
                            )
                            dateLabel.text = it.debt.date.toFormattedDateString()
                            walletLabel.text =
                                mainViewModel.currentAccount.value!!.wallets.find { wallet -> wallet.id == it.debt.walletId }?.name
                        }
                    }
                    debtTransactionAdapter.updateItems(
                        groupTransactionsByDate(
                            it?.transactions ?: emptyList()
                        )
                    )

                }
            }
        }
    }

    override fun setOnClick() {
        super.setOnClick()
        binding.editButton.setOnSafeClickListener {
            val debtToSent = getVM().debtInfo.value!!.debt
            appNavigation.openDebDetailToAddDebtTransactionScreen(Bundle().apply {
                putParcelable("debt", debtToSent)
            })
        }
        binding.deleteButton.setOnSafeClickListener {
            getVM().deleteDebt(debt!!.id)
            appNavigation.navigateUp()
        }

        binding.addDebtAction.setOnSafeClickListener {
            appNavigation.openDebDetailToAddDebtTransactionScreen()
        }

    }

    private fun groupTransactionsByDate(transactions: List<DebtTransaction>): List<DebtListItem> {
        val groupedList = mutableListOf<DebtListItem>()
        var lastDate: String? = null

        for (transaction in transactions) {
            // Use toFormattedDateString to format the date
            val formattedDate = transaction.date.toFormattedDateString("dd MMM yyyy")

            if (formattedDate != lastDate) {
                groupedList.add(DebtListItem.DateHeader(formattedDate))
                lastDate = formattedDate
            }

            groupedList.add(DebtListItem.DebtTransactionItem(transaction))
        }

        return groupedList
    }

    companion object {
        private const val TAG = "DebtDetailFragment"
    }
}

sealed class DebtListItem {
    data class DateHeader(val date: String) : DebtListItem()
    data class DebtTransactionItem(val transaction: DebtTransaction) : DebtListItem()
}
