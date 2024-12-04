package com.example.money_manager_app.fragment.statistic.view

import com.example.money_manager_app.R

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.data.model.CalendarSummary
import com.example.money_manager_app.data.model.Stats
import com.example.money_manager_app.databinding.FragmentStatisticBinding
import com.example.money_manager_app.fragment.statistic.adapter.StatisticAdapter
import com.example.money_manager_app.fragment.statistic.viewmodel.StatisticViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatisticFragment : BaseFragment<FragmentStatisticBinding, StatisticViewModel>(R.layout.fragment_statistic), StatisticAdapter.OnItemClickListener {
    override fun getVM(): StatisticViewModel {
        val viewModel: StatisticViewModel by activityViewModels()
        return viewModel
    }

    private lateinit var statisticAdapter: StatisticAdapter

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        statisticAdapter = StatisticAdapter(requireContext())
        binding.recyclerView.adapter = statisticAdapter
    }

    override fun OnItemClick(v: View, position: Int) {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStatisticBinding.inflate(inflater, container, false)
        return binding.root
    }
}
