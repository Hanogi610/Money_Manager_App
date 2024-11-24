package com.example.money_manager_app.fragment.calendar

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.databinding.FragmentCalendarBinding
import com.example.money_manager_app.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarFragment :
    BaseFragment<FragmentCalendarBinding, CalendarViewModel>(R.layout.fragment_calendar) {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun getVM(): CalendarViewModel {
        val vm: CalendarViewModel by viewModels()
        return vm
    }

    private val event = HashMap<String, String>()


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        calendarView.setOnCalendarDayClickListener(object : OnCalendarDayClickListener {
//            override fun onClick(eventDay: CalendarDay) {
//                val day = String.format("%02d", eventDay.calendar.get(Calendar.DAY_OF_MONTH))
//                val month = String.format("%02d", eventDay.calendar.get(Calendar.MONTH) + 1)
//                val year = eventDay.calendar.get(Calendar.YEAR).toString()
//                val bundle = bundleOf(
//                    "day" to day,
//                    "month" to month,
//                    "year" to year,
//                )
//                val controller = findNavController()
//                controller.navigate(R.id.detailDayFragment, bundle)
//            }
//        })
//        calendarView.setOnPreviousPageChangeListener(object : OnCalendarPageChangeListener {
//            override fun onChange() {
//                val month = String.format("%02d", calendarView.currentPageDate.get(Calendar.MONTH))
//                val year = calendarView.currentPageDate.get(Calendar.YEAR)
//                Toast.makeText(requireContext(), "Month: $month, Year: $year", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
}