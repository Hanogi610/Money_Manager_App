package com.example.money_manager_app.fragment.wallet.add_budget

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.data.model.entity.Budget
import com.example.money_manager_app.data.model.entity.enums.PeriodType
import com.example.money_manager_app.databinding.FragmentAddBudgetBinding
import com.example.money_manager_app.utils.setOnSafeClickListener
import com.example.money_manager_app.utils.toDateTimestamp
import com.example.money_manager_app.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddBudgetFragment : BaseFragment<FragmentAddBudgetBinding, AddBudgetViewModel>(R.layout.fragment_add_budget) {

    private var budget: Budget? = null
    private val mainViewModel: MainViewModel by activityViewModels()
    private val calendar = Calendar.getInstance()

    override fun getVM(): AddBudgetViewModel {
        val vm: AddBudgetViewModel by viewModels()
        return vm
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        arguments?.let {
            budget = it.getParcelable("budget")
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        val periodTypeAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item,
            PeriodType.entries.map { it.name })
        binding.periodSpinner.adapter = periodTypeAdapter

        // Pre-fill fields if editing
        budget?.let { budget ->
            binding.editTextName.setText(budget.name)
            binding.editTextAmount.setText(getString(R.string.money_amount, "", budget.amount))
            binding.editTextStartDate.text =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(budget.periodDateStart)

            val periodTypeIndex = PeriodType.entries.indexOf(budget.periodType)
            binding.periodSpinner.setSelection(periodTypeIndex)
        }

        // Default setup for date fields
        if (budget == null) {
            binding.editTextStartDate.text =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
        }

        binding.editTextName.addTextChangedListener(textWatcher)
        binding.editTextAmount.addTextChangedListener(textWatcher)
        binding.editTextStartDate.addTextChangedListener(textWatcher)
    }

    override fun initToolbar() {
        super.initToolbar()

        binding.backArrowButton.setOnClickListener {
            appNavigation.navigateUp()
        }

        binding.saveButton.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val amount = binding.editTextAmount.text.toString().toDouble()
            val startDate = binding.editTextStartDate.text.toString().toDateTimestamp()
            val periodType =
                PeriodType.valueOf(binding.periodSpinner.selectedItem.toString())
//
//            // Create or update budget
//            val budget = budget?.copy(
//                name = name,
//                amount = amount,
//                periodDateStart = startDate,
//                periodType = periodType
//            ) ?: Budget(
//                name = name,
//                amount = amount,
//                periodDateStart = startDate,
//                periodType = periodType,
//                accountId = mainViewModel.currentAccount.value!!.account.id
//            )
//
//            Log.d("hoangph", "budget: $budget")
//
//            if (budget == null) {
//                getVM().addBudget(budget)
//            } else {
//                getVM().updateBudget(budget)
//            }

            appNavigation.navigateUp()
        }
    }

    override fun setOnClick() {
        super.setOnClick()

        binding.editTextStartDate.setOnSafeClickListener {
            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    binding.startDateTextView.text = SimpleDateFormat(
                        "dd/MM/yyyy", Locale.getDefault()
                    ).format(binding.editTextStartDate.text ?: selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            checkSaveButton()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun checkSaveButton() {
        val name = binding.editTextName.text.toString()
        val amount = binding.editTextAmount.text.toString().toDoubleOrNull()
        val startDate = binding.editTextStartDate.text.toString()
        binding.saveButton.isEnabled =
            amount != null && name.isNotEmpty() && startDate.isNotEmpty()
    }
}