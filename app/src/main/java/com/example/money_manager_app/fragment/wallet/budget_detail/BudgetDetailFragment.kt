package com.example.money_manager_app.fragment.wallet.budget_detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.money_manager_app.R

class BudgetDetailFragment : Fragment() {

    companion object {
        fun newInstance() = BudgetDetailFragment()
    }

    private val viewModel: BudgetDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_budget_detail, container, false)
    }
}