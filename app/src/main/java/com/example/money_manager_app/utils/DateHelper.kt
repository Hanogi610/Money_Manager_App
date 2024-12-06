package com.example.money_manager_app.utils

import android.content.Context
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateHelper {
     fun isNotSameYear(date: Date?): Boolean {
        val calendar: Calendar = Calendar.getInstance()
        val calendar2: Calendar = Calendar.getInstance()
        calendar.setTime(date)
        return calendar.get(1) !== calendar2.get(1)
    }

    fun isSameDay(date1: Long, date2: Long): Boolean {
        val calendar = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()
        calendar.timeInMillis = date1
        calendar2.timeInMillis = date2
        return calendar[1] == calendar2[1] && calendar[6] == calendar2[6]
    }

    fun getFormattedDate(context: Context?, date: Date): String {
        return SimpleDateFormat(
            DateFormat.getBestDateTimePattern(
                Locale.getDefault(),
                "yyyy/MM/dd"
            ), Locale.getDefault()
        ).format(date.time)
    }


}