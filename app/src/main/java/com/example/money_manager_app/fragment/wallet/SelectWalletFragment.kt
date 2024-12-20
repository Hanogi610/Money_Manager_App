package com.example.money_manager_app.fragment.wallet

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.data.model.entity.Wallet
import com.example.money_manager_app.databinding.FragmentSelectWalletBinding
import com.example.money_manager_app.fragment.add.view.expense.ExpenseViewModel
import com.example.money_manager_app.fragment.add.view.income.IncomeViewModel
import com.example.money_manager_app.fragment.add.view.transfer.TransferViewModel
import com.example.money_manager_app.fragment.add.viewmodel.AddViewModel
import com.example.money_manager_app.fragment.wallet.adapter.SelectWalletAdapter
import com.example.money_manager_app.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectWalletFragment : BaseFragment<FragmentSelectWalletBinding, WalletViewModel>(R.layout.fragment_select_wallet) {

    private var selectWalletAdapterInclude: SelectWalletAdapter = SelectWalletAdapter(emptyList(), ::onItemClick)
    private var selectWalletAdapterExclude: SelectWalletAdapter = SelectWalletAdapter(emptyList(), ::onItemClick)
    private val mainViewModel: MainViewModel by activityViewModels()
    private val addViewModel: AddViewModel by activityViewModels()
    private val incomeViewModel : IncomeViewModel by activityViewModels()
    private val expenseViewModel : ExpenseViewModel by activityViewModels()
    private val transferViewModel : TransferViewModel by activityViewModels()

    override fun getVM(): WalletViewModel {
        val vm: WalletViewModel by activityViewModels()
        return vm
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setAdapter()
        observeData()
        getData()
        back()
    }

    fun back(){
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }



    private fun getData(){
        getVM().getWallets(mainViewModel.currentAccount.value!!.account.id)
    }

    private fun observeData(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                getVM().wallets.collect {
                    var listWalletInclude = mutableListOf<Wallet>()
                    var listWalletExclude = mutableListOf<Wallet>()
                    for(wallet in it){
                        if(wallet.wallet.isExcluded == false){
                            listWalletInclude.add(wallet.wallet)
                        }else{
                            listWalletExclude.add(wallet.wallet)
                        }
                    }
                    selectWalletAdapterInclude.setUpdateDataWallet(listWalletInclude)
                    selectWalletAdapterExclude.setUpdateDataWallet(listWalletExclude)
                }
            }
        }
    }


    fun setAdapter() {
        binding.lvIncludeInToal.layoutManager = LinearLayoutManager(requireContext())
        binding.lvIncludeInToal.adapter = selectWalletAdapterInclude

        binding.lvExcludedfromtotal.layoutManager = LinearLayoutManager(requireContext())
        binding.lvExcludedfromtotal.adapter = selectWalletAdapterExclude
    }


    fun onItemClick(wallet: Wallet){
        val bundle = arguments
        var isCheckWallet = bundle?.getBoolean("isCheckWallet")
        var typeExpense = bundle?.getInt("typeExpense")
        if(isCheckWallet == true){
            var listWallet = mutableListOf<Wallet>()
            listWallet.add(wallet)
            addViewModel.setPosition(typeExpense?:0)
            when(typeExpense){
                0 -> incomeViewModel.setFromWallet(listWallet)
                1 -> expenseViewModel.setFromWallet(listWallet)
                2 -> transferViewModel.setFromWallet(listWallet)
            }
            findNavController().popBackStack()
        }else{
            var listWallet = mutableListOf<Wallet>()
            listWallet.add(wallet)
            transferViewModel.setToWallet(listWallet)
            addViewModel.setPosition(typeExpense?:0)
            findNavController().popBackStack()
        }
    }

}