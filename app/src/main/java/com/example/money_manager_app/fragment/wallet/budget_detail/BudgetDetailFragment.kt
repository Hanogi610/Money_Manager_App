package com.example.money_manager_app.fragment.wallet.budget_detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.databinding.FragmentBudgetDetailBinding

class BudgetDetailFragment : BaseFragment<FragmentBudgetDetailBinding, BudgetDetailViewModel>(R.layout.fragment_budget_detail) {

    override fun getVM(): BudgetDetailViewModel {
        val viewModel: BudgetDetailViewModel by viewModels()
        return viewModel
    }

}