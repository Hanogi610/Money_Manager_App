package com.example.money_manager_app.fragment.add.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragmentNotRequireViewModel
import com.example.money_manager_app.databinding.FragmentAddTranferBinding


class AddTranferFragment : BaseFragmentNotRequireViewModel<FragmentAddTranferBinding>(R.id.addTranferFragment) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navGraph()
        back()

    }

    fun back(){
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    fun navGraph() {
        val controller_nav = findNavController()
        binding.tvAddExpense.setOnClickListener {
            controller_nav.navigate(R.id.addExpenseFragment)
        }
        binding.tvIncome.setOnClickListener {
            controller_nav.navigate(R.id.addIncomeFragment)
        }
    }

}