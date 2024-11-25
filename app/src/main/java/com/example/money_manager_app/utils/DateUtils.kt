package com.example.money_manager_app.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Long.toFormattedTimeString(format: String = "HH:mm"): String {
    val time = Date(this)
    val timeFormatter = SimpleDateFormat(format, Locale.getDefault())
    return timeFormatter.format(time)
}

fun Long.toDate(): String {
    return Date(this).toString()
}

fun Long.toFormattedDateString(format: String = "dd/MM/yyyy"): String {
    val date = Date(this)
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
    return dateFormatter.format(date)
}

fun String.toDateTimestamp(format: String = "dd/MM/yyyy"): Long {
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    val date = sdf.parse(this)
    return date?.time ?: 0L
}

fun String.toTimeTimestamp(format: String = "HH:mm"): Long {
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    val time = sdf.parse(this)
    return time?.time ?: 0L
}

fun Long.formatToMonthYear(): String {
    val date = Calendar.getInstance().apply { timeInMillis = this@formatToMonthYear }.time
    return SimpleDateFormat("MMM yyyy", Locale.getDefault()).format(date)
}

fun Long.formatToDayOfMonth(): String {
    val date = Calendar.getInstance().apply { timeInMillis = this@formatToDayOfMonth }.time
    return SimpleDateFormat("dd", Locale.getDefault()).format(date)
}

fun Long.formatToDayOfWeek(): String {
    val date = Calendar.getInstance().apply { timeInMillis = this@formatToDayOfWeek }.time
    return SimpleDateFormat("EEEE", Locale.getDefault()).format(date)
}