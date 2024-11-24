package com.example.money_manager_app.fragment.main

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.data.model.entity.AccountWithWallet
import com.example.money_manager_app.databinding.FragmentMainScreenBinding
import com.example.money_manager_app.viewmodel.MainViewModel
import com.example.moneymanager.ui.main_screen.adapter.MainPagerAdapter
import com.example.moneymanager.ui.main_screen.fragment.AccountSelectorBottomSheet
import kotlinx.coroutines.launch

class MainScreenFragment :
    BaseFragment<FragmentMainScreenBinding, MainScreenViewModel>(R.layout.fragment_main_screen) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var vpAdapter: MainPagerAdapter
    override fun getVM(): MainScreenViewModel {
        val vm: MainScreenViewModel by viewModels()
        return vm
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        vpAdapter = MainPagerAdapter(this)
        binding.mainViewPager.adapter = vpAdapter // Make sure to set the adapter

        // Synchronize BottomNavigationView with ViewPager2
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> binding.mainViewPager.currentItem = 0
                R.id.action_calendar -> binding.mainViewPager.currentItem = 1
                R.id.action_statistic -> binding.mainViewPager.currentItem = 2
                R.id.action_wallet -> binding.mainViewPager.currentItem = 3
            }
            true
        }

        // Synchronize ViewPager2 with BottomNavigationView
        binding.mainViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                getVM().setCurrentFragmentId(position)
                when (position) {
                    0 -> binding.bottomNavigation.selectedItemId = R.id.action_home
                    1 -> binding.bottomNavigation.selectedItemId = R.id.action_calendar
                    2 -> binding.bottomNavigation.selectedItemId = R.id.action_statistic
                    3 -> binding.bottomNavigation.selectedItemId = R.id.action_wallet
                }
            }
        })

        binding.addFab.setOnClickListener {
            when (getVM().currentFragmentId.value) {
                0 -> {
                    // Action for Home Fragment
                    findNavController().navigate(R.id.addExpenseFragment)
                }

                1 -> {
                    // Action for Calendar Fragment
//                    findNavController().navigate(R.id.calendarFragment)
                }

                2 -> {
                    // Action for Statistic Fragment
//                    findNavController().navigate(R.id.addStatisticFragment)
                }

                3 -> {
                    // Action for Wallet Fragment
//                    findNavController().navigate(R.id.addWalletFragment)
                }
            }
        }

        binding.profileName.setOnClickListener {
            mainViewModel.accounts.value.let { accounts ->
                mainViewModel.currentAccount.value?.let { currentAccount ->
                    openAccountSelector(accounts, currentAccount)
                }
            }
        }

        binding.dropdownIcon.setOnClickListener {
            mainViewModel.accounts.value.let { accounts ->
                mainViewModel.currentAccount.value?.let { currentAccount ->
                    openAccountSelector(accounts, currentAccount)
                }
            }
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.currentAccount.collect {
                    it?.let {
                        binding.profileName.text = it.account.nameAccount
                    }
                }
            }
        }
    }

    private fun openAccountSelector(
        accounts: List<AccountWithWallet>, currentAccount: AccountWithWallet
    ) {
        val accountSelector = AccountSelectorBottomSheet(accounts,
            currentAccount,
            { account -> selectAccount(account) },
            { addAccount() })

        accountSelector.show(parentFragmentManager, "AccountSelectorBottomSheet")
    }

    private fun selectAccount(account: AccountWithWallet) {
        mainViewModel.setCurrentAccount(account)
    }

    private fun addAccount() {
        appNavigation.openMainScreenToCreateAccountScreen()
    }
}
