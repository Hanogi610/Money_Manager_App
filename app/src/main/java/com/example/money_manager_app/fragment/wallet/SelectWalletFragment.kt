package com.example.money_manager_app.fragment.wallet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.entity.Wallet
import com.example.money_manager_app.databinding.FragmentSelectWalletBinding
import com.example.money_manager_app.fragment.add.viewmodel.AddViewModel
import com.example.money_manager_app.fragment.wallet.adapter.SelectWalletAdapter
import com.example.money_manager_app.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectWalletFragment : Fragment() {

    private lateinit var binding : FragmentSelectWalletBinding
    private var selectWalletAdapterInclude: SelectWalletAdapter = SelectWalletAdapter(emptyList(), ::onItemClick)
    private var selectWalletAdapterExclude: SelectWalletAdapter = SelectWalletAdapter(emptyList(), ::onItemClick)
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: WalletViewModel by activityViewModels()
    private val addViewModel : AddViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectWalletBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        observeData()
        getData()
    }



    fun getData(){
        viewModel.getWallets(mainViewModel.currentAccount.value!!.account.id)
    }

    fun observeData(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.wallets.collect {
                    var listWalletInclude = mutableListOf<Wallet>()
                    var listWalletExclude = mutableListOf<Wallet>()
                    for(wallet in it){
                        if(wallet.isExcluded == false){
                            listWalletInclude.add(wallet)
                        }else{
                            listWalletExclude.add(wallet)
                        }
                    }
                    Log.d("SelectWalletFragment", "observeData: $listWalletInclude")
                    Log.d("SelectWalletFragment", "observeData: $listWalletExclude")
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
        Log.d("SelectWalletFragment", "onItemClick: $typeExpense")
        Log.d("SelectWalletFragment", "onItemClick: $isCheckWallet")
        if(isCheckWallet == true){
            var listWallet = mutableListOf<Wallet>()
            listWallet.add(wallet)
            addViewModel.setFromWallet(listWallet)
            bundle?.putInt("position", typeExpense?:0)
            findNavController().navigate(R.id.addFragment, bundle)
        }else{
            var listWallet = mutableListOf<Wallet>()
            listWallet.add(wallet)
            addViewModel.setToWallet(listWallet)
            bundle?.putInt("position", typeExpense?:0)
            findNavController().navigate(R.id.addFragment)
        }
    }

}