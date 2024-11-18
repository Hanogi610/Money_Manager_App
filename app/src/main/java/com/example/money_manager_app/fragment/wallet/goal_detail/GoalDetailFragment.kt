package com.example.money_manager_app.fragment.wallet.goal_detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.money_manager_app.R

class GoalDetailFragment : Fragment() {

    companion object {
        fun newInstance() = GoalDetailFragment()
    }

    private val viewModel: GoalDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_goal_detail, container, false)
    }
}