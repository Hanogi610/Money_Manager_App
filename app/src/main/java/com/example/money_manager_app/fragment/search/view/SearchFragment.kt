package com.example.money_manager_app.fragment.search.view

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_manager_app.fragment.search.viewmodel.SearchViewModel
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.databinding.FragmentSearchBinding
import com.example.money_manager_app.fragment.search.adapter.SearchInterface
import com.example.money_manager_app.fragment.search.adapter.SearchTransactionAdapter
import com.example.money_manager_app.utils.setOnSafeClickListener
import com.example.money_manager_app.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(R.layout.fragment_search),
    SearchInterface {

    private val searchViewModel : SearchViewModel by activityViewModels()
    private val mainViewModel : MainViewModel by activityViewModels()
    private val adapter = SearchTransactionAdapter(listOf(), listOf())

    override fun getVM(): SearchViewModel {
        val viewModel: SearchViewModel by viewModels()
        return viewModel
    }


    override fun setOnClick() {
        super.setOnClick()
        binding.filterButton.setOnSafeClickListener {
            showFilterBottomSheet()
            searchViewModel.clearFilter()
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        back()
        setAdapter()
        observeData()
    }

    private fun setAdapter() {
        mainViewModel.getAccount()
        binding.searchResults.layoutManager = LinearLayoutManager(context)
        binding.searchResults.adapter = adapter

    }

    private fun observeData() {
        lifecycleScope.launch {
            searchViewModel.listTransaction.collect { list ->
                adapter.setTransfers(list)
            }
        }

        lifecycleScope.launch {
            mainViewModel.accounts.collect { wallet ->
               adapter.setWallets(wallet.first().wallets)
            }
        }
    }

    fun back() {
        binding.backButton.setOnSafeClickListener {
            searchViewModel.clearFilter()
            findNavController().navigate(R.id.mainFragment)
        }
    }

    private fun showFilterBottomSheet() {
        val filterBottomSheet = FilterBottomSheetDialogFragment()
        filterBottomSheet.setSearchInterface(this)
        filterBottomSheet.show(parentFragmentManager, "FilterBottomSheetDialogFragment")
    }

    override fun search() {
        searchViewModel.searchByDateAndAmountAndDesAndCategoryAndWallet()
    }

}
