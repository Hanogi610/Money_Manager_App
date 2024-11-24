package com.example.money_manager_app.fragment.wallet.add_goal_transaction

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.databinding.FragmentAddGoalTransactionBinding

class AddGoalTransactionFragment : BaseFragment<FragmentAddGoalTransactionBinding, AddGoalTransactionViewModel>(R.layout.fragment_add_goal_transaction) {

    private val viewModel: AddGoalTransactionViewModel by viewModels()
    override fun getVM(): AddGoalTransactionViewModel {
        val vm: AddGoalTransactionViewModel by viewModels()
        return vm
    }

}