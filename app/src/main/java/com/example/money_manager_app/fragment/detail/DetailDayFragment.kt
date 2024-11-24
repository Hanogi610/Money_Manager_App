package com.example.money_manager_app.fragment.detail

import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.databinding.FragmentDetailDayBinding

class DetailDayFragment :
    BaseFragment<FragmentDetailDayBinding, DetailViewModel>(R.layout.fragment_detail_day) {

    override fun getVM(): DetailViewModel {
        val vm: DetailViewModel = DetailViewModel()
        return vm
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val bundle = arguments
//        ShowDay(bundle!!)
//    }
//
//    fun ShowDay(bundle: Bundle) {
//        val day = bundle.getString("day")
//        val month = bundle.getString("month")
//        val year = bundle.getString("year")
//        Toast.makeText(requireContext(), "Day: $day, Month: $month, Year: $year", Toast.LENGTH_SHORT).show()
//        binding.tvDate.text = day.toString()
//        val calendar = Calendar.getInstance()
//        calendar.set(year!!.toInt(), month!!.toInt() - 1, day!!.toInt()) // Trừ 1 cho tháng
//        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
//        binding.tvDayOfWeek.text = CurrencyTypeConverter().getDayOfWeekString(dayOfWeek)
//        binding.tvMonthYear.text = "th $month $year"
//    }
}