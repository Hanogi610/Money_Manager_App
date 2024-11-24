package com.example.money_manager_app.fragment.wallet

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_manager_app.R
import com.example.money_manager_app.viewmodel.MainViewModel
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.data.model.entity.Debt
import com.example.money_manager_app.data.model.entity.Goal
import com.example.money_manager_app.data.model.entity.Wallet
import com.example.money_manager_app.databinding.FragmentWalletBinding
import com.example.money_manager_app.fragment.wallet.adapter.DebtAdapter
import com.example.money_manager_app.fragment.wallet.adapter.GoalAdapter
import com.example.money_manager_app.fragment.wallet.adapter.WalletAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding,WalletViewModel>(R.layout.fragment_wallet) {

    override fun getVM(): WalletViewModel {
        val vm: WalletViewModel by viewModels()
        return vm
    }

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var walletAdapter: WalletAdapter
    private lateinit var debtAdapter: DebtAdapter
    private lateinit var goalAdapter: GoalAdapter

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        val currentCurrency = mainViewModel.currentAccount.value!!.account.currency
        val currencySymbol = getString(currentCurrency.symbolRes)

        walletAdapter = WalletAdapter(requireContext(), currencySymbol, ::onWalletItemClick, ::onAddWalletClick)
        binding.walletRecyclerView.adapter = walletAdapter
        binding.walletRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        debtAdapter = DebtAdapter(requireContext(),currencySymbol,::onDebtItemClick,::onAddDebtClick)
        binding.debtRecyclerView.adapter = debtAdapter
        binding.debtRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        goalAdapter = GoalAdapter(requireContext(),::onGoalItemClick,::onAddGoalClick)
        binding.goalRecyclerView.adapter = goalAdapter
        binding.goalRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun bindingStateView() {
        super.bindingStateView()

        mainViewModel.currentAccount.value?.account?.id?.let {
            getVM().getWallets(it)
            getVM().getDebts(it)
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getVM().wallets.collect { wallets ->
                    walletAdapter.setWallets(wallets)
                    binding.managerTextView.text = getString(R.string.manager, wallets.size)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getVM().debts.collect { debts ->
                    debtAdapter.submitList(debts)
                    binding.debtManagerTextView.text = getString(R.string.manager, debts.size)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getVM().goals.collect { goals ->
                    goalAdapter.submitList(goals)
                    binding.goalManagerTextView.text = getString(R.string.manager, goals.size)
                }
            }
        }
    }

    private fun onGoalItemClick(goal: Goal) {
        appNavigation.openMainScreenToGoalDetailScreen(Bundle().apply {
            putParcelable("goal", goal)
        })
    }

    private fun onAddGoalClick() {
        appNavigation.openMainScreenToAddGoalScreen()
    }

    private fun onDebtItemClick(debt: Debt) {
        appNavigation.openMainScreenToDebtDetailScreen(Bundle().apply {
            putParcelable("debt", debt)
        })
    }

    private fun onAddDebtClick() {
        appNavigation.openMainScreenToAddDebtScreen()
    }

    private fun onAddWalletClick() {
        TODO("Not yet implemented")
    }

    private fun onWalletItemClick(wallet: Wallet) {
        TODO("Not yet implemented")
    }
}