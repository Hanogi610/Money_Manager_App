package com.example.money_manager_app.fragment.search

import androidx.fragment.app.viewModels
import com.example.money_maLnager_app.fragment.search.SearchViewModel
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.databinding.FragmentSearchBinding
import com.example.money_manager_app.utils.setOnSafeClickListener
import com.example.money_manager_app.fragment.search.fragment.FilterBottomSheetDialogFragment

class SearchFragment :
    BaseFragment<FragmentSearchBinding, SearchViewModel>(R.layout.fragment_search) {

    override fun getVM(): SearchViewModel {
        val viewModel: SearchViewModel by viewModels()
        return viewModel
    }

    override fun setOnClick() {
        super.setOnClick()

        binding.filterButton.setOnSafeClickListener {
            showFilterBottomSheet()
        }
    }

    // Show the FilterBottomSheetDialogFragment
    private fun showFilterBottomSheet() {
        val filterBottomSheet = FilterBottomSheetDialogFragment()
        filterBottomSheet.show(parentFragmentManager, "FilterBottomSheetDialogFragment")
    }
}
