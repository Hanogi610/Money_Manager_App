package com.example.money_manager_app.fragment.wallet.goal_detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.data.model.entity.Goal
import com.example.money_manager_app.databinding.FragmentGoalDetailBinding
import com.example.money_manager_app.fragment.wallet.adapter.GoalTransactionAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GoalDetailFragment :
    BaseFragment<FragmentGoalDetailBinding, GoalDetailViewModel>(R.layout.fragment_goal_detail) {

    private var goal: Goal? = null
    private lateinit var goalTransactionAdapter: GoalTransactionAdapter
    override fun getVM(): GoalDetailViewModel {
        val vm: GoalDetailViewModel by viewModels()
        return vm
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        arguments?.let {
            goal = it.getParcelable("goal")
            getVM().getGoalDetail(goal!!.id)
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getVM().goalDetailItem.collect {
                    it?.let {
                        binding.apply {
                            nameLabel.text = it.title
                            saveLabel.text = it.saveAmount.toString()
                            remainLabel.text = it.remainAmount.toString()
                            goalDateLabel.text = it.goalDate
                            timeLabel.text = it.daysLeft
                            progressBar.progress = it.progress
                        }
                    }
                }
            }
        }

    }
}