package com.example.money_manager_app.utils

import com.example.money_manager_app.data.model.Transaction
import com.example.money_manager_app.data.model.TransactionListItem
import com.example.money_manager_app.data.model.entity.Debt
import com.example.money_manager_app.data.model.entity.DebtTransaction
import com.example.money_manager_app.data.model.entity.GoalTransaction
import com.example.money_manager_app.data.model.entity.enums.DebtActionType
import com.example.money_manager_app.data.model.entity.enums.DebtType
import com.example.money_manager_app.data.model.entity.enums.GoalInputType

fun List<Transaction>.groupTransactionsByDate(): List<TransactionListItem> {
    val groupedList = mutableListOf<TransactionListItem>()
    var lastDate: String? = null
    var dailyTotal = 0.0

    for (transaction in this) {
        val dayOfMonth = transaction.date.formatToDayOfMonth()
        val dayOfWeek = transaction.date.formatToDayOfWeek()
        val monthYear = transaction.date.formatToMonthYear()

        if (dayOfMonth != lastDate) {
            if (lastDate != null) {
                groupedList.add(
                    TransactionListItem.DateHeader(
                        lastDate, dayOfWeek, monthYear, dailyTotal
                    )
                )
            }
            lastDate = dayOfMonth
            dailyTotal = 0.0
        }

        when (transaction) {
            is GoalTransaction -> {
                dailyTotal += if (transaction.type == GoalInputType.WITHDRAW) {
                    transaction.amount
                } else {
                    -transaction.amount
                }
            }

            is Debt -> {
                dailyTotal += if (transaction.type == DebtType.RECEIVABLE) {
                    transaction.amount
                } else {
                    -transaction.amount
                }
            }

            is DebtTransaction -> {
                dailyTotal += if (transaction.action == DebtActionType.DEBT_INCREASE) {
                    transaction.amount
                } else {
                    -transaction.amount
                }
            }
        }

        groupedList.add(TransactionListItem.TransactionItem(transaction))
    }

    if (lastDate != null) {
        groupedList.add(
            TransactionListItem.DateHeader(
                lastDate,
                this.last().date.formatToDayOfWeek(),
                this.last().date.formatToMonthYear(),
                dailyTotal
            )
        )
    }

    return groupedList
}