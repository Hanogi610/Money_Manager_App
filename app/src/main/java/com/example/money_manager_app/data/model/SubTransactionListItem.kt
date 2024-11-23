package com.example.money_manager_app.data.model

sealed class SubTransactionListItem {
    data class DateHeader(
        val dayOfMonth: String,
        val dayOfWeek: String,
        val monthYear: String,
        val total: Double
    ) : SubTransactionListItem()

    data class GoalTransactionItem(val transaction: SubTransaction) : SubTransactionListItem()
}