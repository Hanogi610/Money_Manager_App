package com.example.money_manager_app.fragment.statistic.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import com.example.money_manager_app.R
import com.example.money_manager_app.base.BaseBottomSheet
import com.example.money_manager_app.databinding.SheetDateTimeBinding
import java.util.Calendar

class SelectDateBottomSheet : BaseBottomSheet<SheetDateTimeBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.sheet_date_time
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startDate.setOnClickListener {
            showDatePicker { selectedDate ->
                binding.startDate.setText(selectedDate)
            }
        }

        binding.dateLabel2.setOnClickListener {
            showDatePicker { selectedDate ->
                binding.enddate.setText(selectedDate)
            }
        }
    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                onDateSelected(selectedDate)
            }, year, month, day
        )
        datePickerDialog.show()
    }
}