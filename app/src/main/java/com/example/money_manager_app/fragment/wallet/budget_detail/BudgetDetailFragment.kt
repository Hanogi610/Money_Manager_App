package com.example.money_manager_app.fragment.wallet.budget_detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_manager_app.R
import com.example.money_manager_app.adapter.TransactionAdapter
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.data.model.CategoryTransactionDetail
import com.example.money_manager_app.data.model.Transaction
import com.example.money_manager_app.data.model.entity.Budget
import com.example.money_manager_app.data.model.entity.BudgetWithCategory
import com.example.money_manager_app.data.model.entity.CategoryWithTransfer
import com.example.money_manager_app.data.model.entity.Transfer
import com.example.money_manager_app.data.model.entity.enums.TransferType
import com.example.money_manager_app.databinding.FragmentBudgetDetailBinding
import com.example.money_manager_app.fragment.wallet.WalletViewModel
import com.example.money_manager_app.fragment.wallet.adapter.CategoryTransactionAdapter
import com.example.money_manager_app.selecticon.viewmodel.CategoryViewModel
import com.example.money_manager_app.utils.toFormattedDateString
import com.example.money_manager_app.utils.toFormattedTimeString
import com.example.money_manager_app.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@AndroidEntryPoint
class BudgetDetailFragment : BaseFragment<FragmentBudgetDetailBinding, BudgetDetailViewModel>(R.layout.fragment_budget_detail) {

    override fun getVM(): BudgetDetailViewModel {
        val viewModel: BudgetDetailViewModel by activityViewModels()
        return viewModel
    }

    private var budget: Budget? = null
    private var currencySymbol = ""
    private val walletViewModel: WalletViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var categoryTransactionAdapter: CategoryTransactionAdapter

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val currentCurrency = mainViewModel.currentAccount.value!!.account.currency
        currencySymbol = getString(currentCurrency.symbolRes)
        setAdapter()
        observeData()
        imgBack()
    }

    private fun imgBack() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setAdapter() {
        categoryTransactionAdapter = CategoryTransactionAdapter(listOf(),currencySymbol, ::onTransactionItemClick)
        binding.transactionRv.adapter = categoryTransactionAdapter
        binding.transactionRv.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeData() {
        lifecycleScope.launch {
            getVM().listCategoryWithTransfer.collect {
                showData(it)
            }
        }
        lifecycleScope.launch {
            getVM().budgetsWithCategory.collect {
                val budgetWithCategory = it.find { it.budget.id == budget!!.id }
                if(budgetWithCategory != null){
                    Log.d("BudgetDetailFragment", "budgetWithCategory is ${budgetWithCategory}")
                    getVM().getCategoryWithTransfer(budgetWithCategory)
                }
            }
        }
    }

    fun onTransactionItemClick(transfer: Transfer) {

    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        budget = arguments?.getParcelable<Budget>("budget")
        if(budget != null){
            binding.nameLabel.text = budget!!.name
            binding.spentLabel.text =  "${currencySymbol}" + budget!!.spent.toString()
            binding.progressBar.max = budget!!.amount.toInt()
            if(budget!!.spent > budget!!.amount){
                binding.remainTitleLabel.text = "Overspent"
                binding.progressBar.progress = budget!!.amount.toInt()
            } else {
                binding.remainTitleLabel.text = "Left"
                binding.progressBar.progress = budget!!.spent.toInt()
            }
            binding.remainLabel.text = "${currencySymbol}" + (budget!!.amount - budget!!.spent).toString()
            getVM().getBudgets(budget!!.accountId)
            var budgetWithCategory = getVM().budgetsWithCategory.value?.find { it.budget.id == budget!!.id }
            var category = ""
            if(budgetWithCategory != null){
                for(i in budgetWithCategory.categories){
                    category += i.name + ", "
                }
            }
            binding.categoryLabel.text = category
            binding.budgetLabel.text = "${currencySymbol}" + budget!!.amount.toString()
            binding.periodLabel.text = budget!!.start_date.toFormattedDateString() + " - " + budget!!.end_date.toFormattedDateString()

//            val currentDate = LocalDate.now()
//            val endDate = LocalDate.parse(budget!!.end_date.toFormattedDateString())
//            val daysBetween = ChronoUnit.DAYS.between(currentDate, endDate)
//            binding.timeLabel.text = "$daysBetween days left"
        }
    }

   fun showData(category: List<CategoryWithTransfer>){
       var listCategoryTransactionDetail = getVM().toCategoryTransactionDetail(category)
         categoryTransactionAdapter.setDate(listCategoryTransactionDetail)
   }

}