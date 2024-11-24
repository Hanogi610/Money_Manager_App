package com.example.money_manager_app.fragment.wallet.add_goal

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.databinding.FragmentAddGoalBinding

class AddGoalFragment : BaseFragment<FragmentAddGoalBinding,AddGoalViewModel>(R.layout.fragment_add_goal) {

    override fun getVM(): AddGoalViewModel {
        val viewModel: AddGoalViewModel by viewModels()
        return viewModel
    }


}