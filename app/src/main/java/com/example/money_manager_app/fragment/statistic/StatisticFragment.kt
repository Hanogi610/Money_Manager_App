package com.example.money_manager_app.fragment.statistic

import androidx.fragment.app.viewModels
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.databinding.FragmentStatisticBinding

class StatisticFragment :
    BaseFragment<FragmentStatisticBinding, StatisticViewModel>(R.layout.fragment_statistic) {

    override fun getVM(): StatisticViewModel {
        val viewModel: StatisticViewModel by viewModels()
        return viewModel
    }

}