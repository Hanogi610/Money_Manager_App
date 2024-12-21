package com.example.money_manager_app.fragment.statistic.view

import com.example.money_manager_app.R


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.data.model.AccountWithWalletItem
import com.example.money_manager_app.data.model.entity.Wallet

import com.example.money_manager_app.databinding.FragmentStatisticBinding
import com.example.money_manager_app.fragment.main.fragment.AccountSelectorBottomSheet
import com.example.money_manager_app.fragment.statistic.adapter.StaticInterface
import com.example.money_manager_app.fragment.statistic.adapter.StatisticAdapter
import com.example.money_manager_app.fragment.statistic.viewmodel.StatisticViewModel
import com.example.money_manager_app.utils.CalendarHelper
import com.example.money_manager_app.utils.DateHelper
import com.example.money_manager_app.utils.TimeType
import com.example.money_manager_app.utils.toDateTimestamp
import com.example.money_manager_app.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class StatisticFragment : BaseFragment<FragmentStatisticBinding, StatisticViewModel>(R.layout.fragment_statistic),View.OnClickListener,StaticInterface{
    private var wallets: List<Wallet> = ArrayList()
    private var date: Date? = null
    private var time : TimeType = TimeType.MONTHLY
    private lateinit var statisticAdapter: StatisticAdapter
    private val mainViewModel : MainViewModel by activityViewModels()

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        date = CalendarHelper.getInitialDate()
        setUpLayoutContent(date, mainViewModel.currentAccount.value!!.account.id)

    }

    override fun getVM(): StatisticViewModel {
        val viewModel: StatisticViewModel by activityViewModels()
        return viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        setUpLayout()
        super.initData(savedInstanceState)
        val wallet = arguments.let {
            it?.getParcelable<Wallet>("wallet")
        }
        if(wallet != null) {
            wallets = listOf(wallet)
            statisticAdapter.setTitle(wallet.name)
        } else {
            wallets = mainViewModel.currentAccount.value!!.walletItems.map { it.wallet }
            statisticAdapter.setTitle("Balance")
        }
        setBalance()
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getVM().wallets.collect{
                    val balance = getVM().getWallets(it)
                    statisticAdapter.setBalance(balance.first,balance.second)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getVM().calendarSummary.collect {
                    statisticAdapter.setOverviewSummary(it)
                    statisticAdapter.setPieStatsList(getVM().listStatsExpense.value)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getVM().listStatsExpense.collect {
                    statisticAdapter.setPieStatsList(it)
                }
            }
        }
    }

    private fun setBalance() {
        getVM().setWallets(wallets)
    }

    private fun setUpLayout() {
        val currentCurrency = mainViewModel.currentAccount.value!!.account.currency
        val currencySymbol = getString(currentCurrency.symbolRes)
        binding.recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        statisticAdapter = StatisticAdapter(requireContext(),currencySymbol,::onClickOverview ,::onClickPie)
        binding.recyclerView.adapter = statisticAdapter
        binding.backImage.setOnClickListener(this)
        binding.nextImage.setOnClickListener(this)
        binding.dateLabel.setOnClickListener(this)
    }

    fun onClickOverview(){
        appNavigation.openStatisticScreenToTransactionScreen(
            Bundle().apply {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dateStart = dateFormat.format(date)
                putString("dateStart",dateStart)
                putParcelable("type", time)
            }
        )
    }

    fun onClickPie(){
        findNavController().navigate(R.id.structureFragment,
            Bundle().apply {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dateStart = dateFormat.format(date)
                putString("dateStart",dateStart)
                putParcelable("type", time)
                putParcelableArrayList("wallets", ArrayList(wallets))
            }
        )
    }




    private fun setUpLayoutContent(date: Date?, idAccount : Long) {
        if(date != null) {
            binding.dateLabel.text = CalendarHelper.getFormattedDailyDate(date)
            when(time) {
                TimeType.MONTHLY -> {
                    binding.dateLabel.text = CalendarHelper.getFormattedDailyDate(date)
                    var dateStart = DateHelper.getDateMonth(date)
                    getVM().getCalendarSummary(wallets,dateStart.first.toDateTimestamp(),dateStart.second.toDateTimestamp(),idAccount)
                }
                TimeType.DAILY -> {
                    binding.dateLabel.text = CalendarHelper.getFormattedDailyDate(date)
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val dateStart = dateFormat.format(date)
                    getVM().getCalendarSummary(wallets,dateStart.toDateTimestamp(),dateStart.toDateTimestamp(), idAccount)
                }
                TimeType.WEEKLY -> {
                    binding.dateLabel.text = CalendarHelper.getFormattedWeeklyDate(requireContext(),date)
                    val dateStart = DateHelper.getDateWeek(date)
                    getVM().getCalendarSummary(wallets,dateStart.first.toDateTimestamp(),dateStart.second.toDateTimestamp(), idAccount)
                }
                TimeType.YEARLY -> {
                    binding.dateLabel.text = CalendarHelper.getFormattedYearlyDate(date)
                    val dateStart = DateHelper.getDateYear(date)
                    getVM().getCalendarSummary(wallets,dateStart.first.toDateTimestamp(),dateStart.second.toDateTimestamp(), idAccount)

                }
                TimeType.ALL -> {
                    binding.dateLabel.text = "All"
                    getVM().getCalendarSummary(wallets, idAccount)

                }
                TimeType.CUSTOM -> {
                    binding.dateLabel.text = "Custom"

                }
            }
        }
    }

    override fun onClick(view: View) {
        when(time) {
            TimeType.MONTHLY -> {
                when (view.id) {
                    R.id.backImage -> {
                        date = CalendarHelper.incrementMonth(date!!, -1)
                        setUpLayoutContent(date,mainViewModel.currentAccount.value!!.account.id)
                    }
                    R.id.dateLabel -> {
                        val filterTimeBottomSheetDialogFragment = FilterTimeBottomSheetDialogFragment()
                        filterTimeBottomSheetDialogFragment.setStaticInterfacce(this)
                        filterTimeBottomSheetDialogFragment.show(parentFragmentManager, filterTimeBottomSheetDialogFragment.tag)
                    }
                    R.id.nextImage -> {
                        date = CalendarHelper.incrementMonth(date!!, +1)
                        setUpLayoutContent(date, mainViewModel.currentAccount.value!!.account.id)
                    }
                }
            }

            TimeType.DAILY -> {
                when (view.id) {
                    R.id.backImage -> {
                        date = CalendarHelper.incrementDay(date!!, -1)
                        setUpLayoutContent(date, mainViewModel.currentAccount.value!!.account.id)
                    }
                    R.id.dateLabel -> {
                        val filterTimeBottomSheetDialogFragment = FilterTimeBottomSheetDialogFragment()
                        filterTimeBottomSheetDialogFragment.setStaticInterfacce(this)
                        filterTimeBottomSheetDialogFragment.show(parentFragmentManager, filterTimeBottomSheetDialogFragment.tag)
                    }
                    R.id.nextImage -> {
                        date = CalendarHelper.incrementDay(date!!, +1)
                        setUpLayoutContent(date, mainViewModel.currentAccount.value!!.account.id)
                    }
                }
            }
            TimeType.WEEKLY -> {
                when (view.id) {
                    R.id.backImage -> {
                        date = CalendarHelper.incrementWeek(date!!, -1)
                        setUpLayoutContent(date, mainViewModel.currentAccount.value!!.account.id)
                    }
                    R.id.dateLabel -> {
                        val filterTimeBottomSheetDialogFragment = FilterTimeBottomSheetDialogFragment()
                        filterTimeBottomSheetDialogFragment.setStaticInterfacce(this)
                        filterTimeBottomSheetDialogFragment.show(parentFragmentManager, filterTimeBottomSheetDialogFragment.tag)
                    }
                    R.id.nextImage -> {
                        date = CalendarHelper.incrementWeek(date!!, +1)
                        setUpLayoutContent(date, mainViewModel.currentAccount.value!!.account.id)
                    }
                }
            }
            TimeType.YEARLY -> {
                when (view.id) {
                    R.id.backImage -> {
                        date = CalendarHelper.incrementYear(date!!, -1)
                        setUpLayoutContent(date, mainViewModel.currentAccount.value!!.account.id)
                    }
                    R.id.dateLabel -> {
                        val filterTimeBottomSheetDialogFragment = FilterTimeBottomSheetDialogFragment()
                        filterTimeBottomSheetDialogFragment.setStaticInterfacce(this)
                        filterTimeBottomSheetDialogFragment.show(parentFragmentManager, filterTimeBottomSheetDialogFragment.tag)
                    }
                    R.id.nextImage -> {
                        date = CalendarHelper.incrementYear(date!!, +1)
                        setUpLayoutContent(date, mainViewModel.currentAccount.value!!.account.id)
                    }
                }
            }
            TimeType.ALL -> {
                when(view.id) {
                    R.id.backImage -> {
                        setUpLayoutContent(date, mainViewModel.currentAccount.value!!.account.id)
                    }
                    R.id.dateLabel -> {
                        val filterTimeBottomSheetDialogFragment = FilterTimeBottomSheetDialogFragment()
                        filterTimeBottomSheetDialogFragment.setStaticInterfacce(this)
                        filterTimeBottomSheetDialogFragment.show(parentFragmentManager, filterTimeBottomSheetDialogFragment.tag)
                    }
                    R.id.nextImage -> {
                        setUpLayoutContent(date, mainViewModel.currentAccount.value!!.account.id)
                    }
                }
            }
            TimeType.CUSTOM -> TODO()
        }
    }

    override fun onClickTime(timeType: TimeType) {
        time = timeType
        date = Date()
        setUpLayoutContent(date, mainViewModel.currentAccount.value!!.account.id)
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

    override fun setOnClick() {
        super.setOnClick()

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

    private fun openAccountSelector(
        accounts: List<AccountWithWalletItem>, currentAccount: AccountWithWalletItem
    ) {
        val accountSelector = AccountSelectorBottomSheet(
            accounts,
            currentAccount,
            { account -> selectAccount(account) },
            ::addAccount,
            mainViewModel.hiddenBalance.value ?: false
        )

        accountSelector.show(parentFragmentManager, "AccountSelectorBottomSheet")
    }

    private fun selectAccount(account: AccountWithWalletItem) {
        mainViewModel.setCurrentAccount(account)

    }

    private fun addAccount() {
        appNavigation.openStatisticScreenToCreateAccountScreen(Bundle().apply {
            putBoolean("isAddAccount", true)
        })
    }
}
