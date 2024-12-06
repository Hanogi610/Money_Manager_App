package com.example.money_manager_app.fragment.statistic.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.money_manager_app.R
import com.example.money_manager_app.base.BaseBottomSheet
import com.example.money_manager_app.data.model.CategoryData
import com.example.money_manager_app.databinding.FilterBottomSheetBinding
import com.example.money_manager_app.databinding.SheetDialogFragmentBinding
import com.example.money_manager_app.fragment.add.viewmodel.AddViewModel
import com.example.money_manager_app.fragment.search.adapter.IconCategoryAdapter
import com.example.money_manager_app.fragment.search.adapter.SearchInterface
import com.example.money_manager_app.fragment.search.viewmodel.SearchViewModel
import com.example.money_manager_app.fragment.statistic.adapter.StaticInterface
import com.example.money_manager_app.fragment.wallet.WalletViewModel
import com.example.money_manager_app.utils.TimeType
import java.util.Calendar

class FilterTimeBottomSheetDialogFragment : BaseBottomSheet<SheetDialogFragmentBinding>() {

    private var staticInterface : StaticInterface? = null

    override fun getLayoutId(): Int {
        return R.layout.sheet_dialog_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectTime()
    }

    fun selectTime(){
        binding.ivAllRadio.setOnClickListener {
            staticInterface?.onClickTime(TimeType.ALL)
            dismiss()
        }
        binding.ivDailyRadio.setOnClickListener {
            staticInterface?.onClickTime(TimeType.DAILY)
            dismiss()
        }
        binding.ivWeeklyRadio.setOnClickListener {
            staticInterface?.onClickTime(TimeType.WEEKLY)
            dismiss()
        }
        binding.ivMonthlyRadio.setOnClickListener {
            staticInterface?.onClickTime(TimeType.MONTHLY)
            dismiss()
        }
        binding.ivYearlyRadio.setOnClickListener {
            staticInterface?.onClickTime(TimeType.YEARLY)
            dismiss()
        }

        binding.ivCustomRadio.setOnClickListener {
            staticInterface?.onClickTime(TimeType.CUSTOM)
            dismiss()
        }

    }

    fun setStaticInterfacce(staticInterface: StaticInterface) {
        this.staticInterface = staticInterface
    }



}
