package com.example.money_manager_app.fragment.statistic.view

import com.example.money_manager_app.R


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.money_manager_app.base.fragment.BaseFragment

import com.example.money_manager_app.databinding.FragmentStatisticBinding
import com.example.money_manager_app.fragment.statistic.adapter.StaticInterface
import com.example.money_manager_app.fragment.statistic.adapter.StatisticAdapter
import com.example.money_manager_app.fragment.statistic.viewmodel.StatisticViewModel
import com.example.money_manager_app.utils.CalendarHelper
import com.example.money_manager_app.utils.TimeType
import java.util.Date
import java.util.Locale

class StatisticFragment : BaseFragment<FragmentStatisticBinding, StatisticViewModel>(R.layout.fragment_statistic),View.OnClickListener,StaticInterface,
    StatisticAdapter.OnItemClickListener {
    override fun getVM(): StatisticViewModel {
        val viewModel: StatisticViewModel by activityViewModels()
        return viewModel
    }

    private var date: Date? = null
    private var time : TimeType = TimeType.MONTHLY
    private lateinit var statisticAdapter: StatisticAdapter

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        date = CalendarHelper.getInitialDate()
        setUpLayoutContent(date)
        setUpLayout()
    }

    private fun setUpLayout() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        statisticAdapter = StatisticAdapter(requireContext())
        binding.recyclerView.adapter = statisticAdapter
        binding.backImage.setOnClickListener(this)
        binding.nextImage.setOnClickListener(this)
        binding.dateLabel.setOnClickListener(this)
    }

    override fun OnItemClick(v: View, position: Int) {
    }


    private fun setUpLayoutContent(date: Date?) {
        if(date != null) {
            binding.dateLabel.text = CalendarHelper.getFormattedDailyDate(date)
            when(time) {
                TimeType.MONTHLY -> {
                    binding.dateLabel.text = CalendarHelper.getFormattedDailyDate(date)
                    binding.backImage.isEnabled = true
                    binding.nextImage.isEnabled = true
                }
                TimeType.DAILY -> {
                    binding.dateLabel.text = CalendarHelper.getFormattedDailyDate(date)
                    binding.backImage.isEnabled = true
                    binding.nextImage.isEnabled = true
                }
                TimeType.WEEKLY -> {
                    binding.dateLabel.text = CalendarHelper.getFormattedWeeklyDate(requireContext(),date)
                    binding.backImage.isEnabled = true
                    binding.nextImage.isEnabled = true
                }
                TimeType.YEARLY -> {
                    binding.dateLabel.text = CalendarHelper.getFormattedYearlyDate(date)
                    binding.backImage.isEnabled = true
                    binding.nextImage.isEnabled = true

                }
                TimeType.ALL -> {
                    binding.dateLabel.text = "All"
                    binding.backImage.isEnabled = false
                    binding.nextImage.isEnabled = false

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
                        setUpLayoutContent(date)
                    }
                    R.id.dateLabel -> {
                        val filterTimeBottomSheetDialogFragment = FilterTimeBottomSheetDialogFragment()
                        filterTimeBottomSheetDialogFragment.setStaticInterfacce(this)
                        filterTimeBottomSheetDialogFragment.show(parentFragmentManager, filterTimeBottomSheetDialogFragment.tag)
                    }
                    R.id.nextImage -> {
                        date = CalendarHelper.incrementMonth(date!!, +1)
                        setUpLayoutContent(date)
                    }
                }
            }

            TimeType.DAILY -> {
                when (view.id) {
                    R.id.backImage -> {
                        date = CalendarHelper.incrementDay(date!!, -1)
                        setUpLayoutContent(date)
                    }
                    R.id.dateLabel -> {
                        val filterTimeBottomSheetDialogFragment = FilterTimeBottomSheetDialogFragment()
                        filterTimeBottomSheetDialogFragment.setStaticInterfacce(this)
                        filterTimeBottomSheetDialogFragment.show(parentFragmentManager, filterTimeBottomSheetDialogFragment.tag)
                    }
                    R.id.nextImage -> {
                        date = CalendarHelper.incrementDay(date!!, +1)
                        setUpLayoutContent(date)
                    }
                }
            }
            TimeType.WEEKLY -> {
                when (view.id) {
                    R.id.backImage -> {
                        date = CalendarHelper.incrementWeek(date!!, -1)
                        setUpLayoutContent(date)
                    }
                    R.id.dateLabel -> {
                        val filterTimeBottomSheetDialogFragment = FilterTimeBottomSheetDialogFragment()
                        filterTimeBottomSheetDialogFragment.setStaticInterfacce(this)
                        filterTimeBottomSheetDialogFragment.show(parentFragmentManager, filterTimeBottomSheetDialogFragment.tag)
                    }
                    R.id.nextImage -> {
                        date = CalendarHelper.incrementWeek(date!!, +1)
                        setUpLayoutContent(date)
                    }
                }
            }
            TimeType.YEARLY -> {
                when (view.id) {
                    R.id.backImage -> {
                        date = CalendarHelper.incrementYear(date!!, -1)
                        setUpLayoutContent(date)
                    }
                    R.id.dateLabel -> {
                        val filterTimeBottomSheetDialogFragment = FilterTimeBottomSheetDialogFragment()
                        filterTimeBottomSheetDialogFragment.setStaticInterfacce(this)
                        filterTimeBottomSheetDialogFragment.show(parentFragmentManager, filterTimeBottomSheetDialogFragment.tag)
                    }
                    R.id.nextImage -> {
                        date = CalendarHelper.incrementYear(date!!, +1)
                        setUpLayoutContent(date)
                    }
                }
            }
            TimeType.ALL -> {
                when(view.id) {
                    R.id.backImage -> {
                        setUpLayoutContent(date)
                    }
                    R.id.dateLabel -> {
                        val filterTimeBottomSheetDialogFragment = FilterTimeBottomSheetDialogFragment()
                        filterTimeBottomSheetDialogFragment.setStaticInterfacce(this)
                        filterTimeBottomSheetDialogFragment.show(parentFragmentManager, filterTimeBottomSheetDialogFragment.tag)
                    }
                    R.id.nextImage -> {
                        setUpLayoutContent(date)
                    }
                }
            }
            TimeType.CUSTOM -> TODO()
        }
    }

    override fun onClickTime(timeType: TimeType) {
        time = timeType
        date = Date()
        setUpLayoutContent(date)
    }


}
