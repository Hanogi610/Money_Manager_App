package com.example.money_manager_app.fragment.wallet.add_budget

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.money_manager_app.R
import com.example.money_manager_app.adapter.ColorSpinnerAdapter
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.data.model.CategoryData
import com.example.money_manager_app.data.model.entity.Budget
import com.example.money_manager_app.data.model.entity.enums.PeriodType
import com.example.money_manager_app.databinding.FragmentAddBudgetBinding
import com.example.money_manager_app.selecticon.viewmodel.CategoryViewModel
import com.example.money_manager_app.utils.ColorUtils
import com.example.money_manager_app.utils.setOnSafeClickListener
import com.example.money_manager_app.utils.toDateTimestamp
import com.example.money_manager_app.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale



@AndroidEntryPoint
class AddBudgetFragment : BaseFragment<FragmentAddBudgetBinding, AddBudgetViewModel>(R.layout.fragment_add_budget) {

    private var budget: Budget? = null
    private val mainViewModel: MainViewModel by activityViewModels()
    private val calendar = Calendar.getInstance()
    private val categoryViewModel: CategoryViewModel by activityViewModels()

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
        selectCategory()
        obseverData()
        selectColor()
        selectPeriod()
        back()
        save()
    }

    fun back(){
        binding.backArrowButton.setOnSafeClickListener {
            findNavController().popBackStack()
        }
    }

    fun selectPeriod(){
        val selectPeriod = resources.getStringArray(R.array.budget_period)

        val periodTypeAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_period,
            selectPeriod
        )
        binding.periodSpinner.adapter = periodTypeAdapter
        binding.periodSpinner.setSelection(0)
    }

    fun selectColor(){
        val colorAdapter = ColorSpinnerAdapter(requireContext(), ColorUtils.getColors(requireContext()))
        binding.colorSpinner.adapter = colorAdapter
        binding.colorSpinner.setSelection(0)
    }

    fun obseverData(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                categoryViewModel.categories.observe(viewLifecycleOwner){
                    showCategory(it)
                }
            }
        }
    }

    fun showCategory(category : List<CategoryData.Category>){
        var nameCategory =""
        if(category[0].isCheck == true){
            nameCategory="All category"
        } else {
            for (i in category.indices){
                if(category[i].isCheck == true){
                    nameCategory += category[i].name +", "
                }
            }
            nameCategory.removeSuffix(", ")
        }
        binding.selectCategory.setText(nameCategory)
    }

    private fun selectCategory() {
        binding.selectCategory.setOnSafeClickListener {
            appNavigation.openAddBudgetToSelectCategory()
        }
    }

    private fun save() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val calendarToday = Calendar.getInstance()
        val todayDate = dateFormat.format(calendarToday.time).toDateTimestamp()
        val calendarNextWeek = Calendar.getInstance()
        calendarNextWeek.add(Calendar.DAY_OF_YEAR, 7)
        val nextWeekDate = dateFormat.format(calendarNextWeek.time).toDateTimestamp()
        Log.d("AddBudgetFragment", "save: ${binding.periodSpinner.selectedItemPosition}")
        binding.saveButton.setOnSafeClickListener {
            val amountText = binding.editTextAmount.text.toString()
            val amount = if (amountText.isNotEmpty()) amountText.toDouble() else 0.0
            if (amount > 0) {
                val budget = Budget(
                    name = binding.editTextName.text.toString(),
                    amount = amount,
                    spent = 0,
                    accountId = mainViewModel.accounts.value.first().account.id,
                    colorId = ColorUtils.getColors(requireContext())[binding.colorSpinner.selectedItemPosition],
                    periodType = PeriodType.entries[binding.periodSpinner.selectedItemPosition],
                    start_date = todayDate,
                    end_date = nextWeekDate
                )
                getVM().insertBudget(
                    budget,
                    mainViewModel.categories.value ?: emptyList(),
                    categoryViewModel.categories.value ?: emptyList()
                )
                findNavController().popBackStack()
            }
        }
    }

    override fun initToolbar() {
        super.initToolbar()

    }
}