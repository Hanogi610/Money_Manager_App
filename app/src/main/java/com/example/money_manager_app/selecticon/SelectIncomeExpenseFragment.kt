package com.example.money_manager_app.selecticon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.entity.CategoryData
import com.example.money_manager_app.data.model.entity.enums.TransferType
import com.example.money_manager_app.databinding.FragmentSelectIncomeExpenseBinding
import com.example.money_manager_app.fragment.add.viewmodel.AddViewModel
import com.example.money_manager_app.selecticon.Adapter.SelectIncomeExpenseAdapter

class SelectIncomeExpenseFragment : Fragment() {
    private var _binding: FragmentSelectIncomeExpenseBinding? = null
    private val binding get() = _binding!!
    private var typeExpense: String? = null
    private var selectIncomeExpenseAdapter = SelectIncomeExpenseAdapter(listOf(),::clickRadioButtonIconCategory)
    private val viewModel: AddViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectIncomeExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        showData()
        setupListeners()
    }

    private fun setupListeners() {
        binding.tvIncome.setOnClickListener {
            updateUIForIncome()
        }
        binding.tvExpense.setOnClickListener {
            updateUIForExpense()
        }
    }

    private fun updateUIForIncome() {
        binding.tvIncome.apply {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            setBackgroundResource(R.drawable.customer_select_category_income)
        }
        binding.tvExpense.apply {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            setBackgroundResource(R.drawable.customer_select_category)
        }
        typeExpense = TransferType.Income.toString()
        selectIncomeExpenseAdapter.updateData(viewModel.getCategoryListIncome())
    }

    private fun updateUIForExpense() {
        binding.tvExpense.apply {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            setBackgroundResource(R.drawable.customer_select_category_expense)
        }
        binding.tvIncome.apply {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            setBackgroundResource(R.drawable.customer_select_category)
        }
        typeExpense = TransferType.Expense.toString()
        selectIncomeExpenseAdapter.updateData(viewModel.getCategoryListExpense())
    }

    private fun initView() {
        typeExpense = arguments?.getString("type")
        when (typeExpense) {
            TransferType.Income.toString() -> updateUIForIncome()
            else -> updateUIForExpense()
        }
    }

    private fun clickRadioButtonIconCategory(categoryData: CategoryData.Category) {
        if (!categoryData.isCheck) {
            if (typeExpense == TransferType.Expense.toString()) {
                viewModel.setOneCategoryExpense(categoryData)
            } else {
                viewModel.setOneCategoryIcome(categoryData)
            }
            val bundle = bundleOf("idCategory" to categoryData.id)
            val destination = if (typeExpense == TransferType.Expense.toString()) {
                bundle.putInt("position", 1)
                R.id.addFragment
            } else {
                bundle.putInt("position", 0)
                R.id.addFragment
            }
            findNavController().navigate(destination, bundle)
        }
    }

    private fun showData() {
        binding.vpCategory.apply {
            adapter = selectIncomeExpenseAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}