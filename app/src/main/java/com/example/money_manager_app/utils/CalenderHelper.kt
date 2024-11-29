package com.example.money_manager_app.utils

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale



object CalendarHelper {

    fun getInitialDate(): Date {
        val calendar = Calendar.getInstance()
        calendar[5] = 1
        return calendar.time
    }


    fun getCalendarDay(date: Date?, i: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar[5] = i
        return calendar.time
    }


    fun incrementMonth(date: Date?, i: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(2, i)
        return calendar.time
    }

    fun getFormattedMonthlyDate(date: Date?): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return SimpleDateFormat(
            DateFormat.getBestDateTimePattern(Locale.getDefault(), "MMMM yyyy"),
            Locale.getDefault()
        ).format(calendar.time)
    }


    fun getDayOfMonth(date: Date?): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.getActualMaximum(5)
    }

    fun getDayOfWeek(date: Date?): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar[5] = 1
        return calendar[7]
    }

    fun isSameMonth(date: Date?): Int {
        val calendar = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()
        calendar2.time = date
        if (calendar[1] == calendar2[1] && calendar[2] == calendar2[2]) {
            return calendar[5]
        }
        return -1
    }


}