package com.example.money_manager_app.fragment.wallet.goal_detail

import androidx.lifecycle.viewModelScope
import com.example.money_manager_app.base.BaseViewModel
import com.example.money_manager_app.data.model.entity.GoalDetail
import com.example.money_manager_app.data.model.entity.GoalTransaction
import com.example.money_manager_app.data.model.entity.enums.GoalInputType
import com.example.money_manager_app.data.repository.GoalRepository
import com.example.money_manager_app.utils.formatToDayOfMonth
import com.example.money_manager_app.utils.formatToDayOfWeek
import com.example.money_manager_app.utils.formatToMonthYear
import com.example.money_manager_app.utils.toFormattedDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class GoalDetailViewModel @Inject constructor(
    private val goalRepository: GoalRepository
) : BaseViewModel() {
    private val _goal = MutableStateFlow<GoalDetail?>(null)
    val goal: StateFlow<GoalDetail?> get() = _goal

    private val _goalDetailItem = MutableStateFlow<GoalDetailItem?>(null)
    val goalDetailItem: StateFlow<GoalDetailItem?> get() = _goalDetailItem

    private val _goalListItems = MutableStateFlow<List<GoalListItem>>(emptyList())
    val goalListItems: StateFlow<List<GoalListItem>> get() = _goalListItems

    fun getGoalDetail(goalId: Long) {
        viewModelScope.launch {
            goalRepository.getGoalById(goalId).collect {
                _goal.value = it
                it.let {
                    _goalDetailItem.value = convertGoalDetailToGoalDetailItem(it)
                }
            }
        }
    }

    private fun convertGoalDetailToGoalDetailItem(goalDetail: GoalDetail): GoalDetailItem {
        val saveAmount = goalDetail.transactions.filter { it.type == GoalInputType.DEPOSIT }
            .sumOf { it.amount } - goalDetail.transactions.filter { it.type == GoalInputType.WITHDRAW }
            .sumOf { it.amount }
        val remainAmount = goalDetail.goal.amount - saveAmount
        val goalDate = goalDetail.goal.targetDate.toFormattedDateString()
        val daysLeft = calculateDaysLeft(goalDetail.goal.targetDate)
        val groupedTransactions = groupTransactionsByDate(goalDetail.transactions)
        return GoalDetailItem(
            title = goalDetail.goal.name,
            saveAmount = saveAmount,
            remainAmount = remainAmount,
            amountGoal = goalDetail.goal.amount,
            goalDate = goalDate,
            daysLeft = daysLeft,
            transactions = groupedTransactions
        )
    }

    private fun calculateDaysLeft(targetDate: Long): String {
        val currentDate = Calendar.getInstance().timeInMillis
        val diffInMillis = targetDate - currentDate
        val daysLeft = TimeUnit.MILLISECONDS.toDays(diffInMillis)
        return daysLeft.toString()
    }

    private fun groupTransactionsByDate(transactions: List<GoalTransaction>): List<GoalListItem> {
        val groupedList = mutableListOf<GoalListItem>()
        var lastDate: String? = null
        var dailyTotal = 0.0

        for (transaction in transactions) {
            val dayOfMonth = transaction.transactionDate.formatToDayOfMonth()
            val dayOfWeek = transaction.transactionDate.formatToDayOfWeek()
            val monthYear = transaction.transactionDate.formatToMonthYear()

            if (dayOfMonth != lastDate) {
                if (lastDate != null) {
                    groupedList.add(
                        GoalListItem.DateHeader(
                            lastDate,
                            dayOfWeek,
                            monthYear,
                            dailyTotal
                        )
                    )
                }
                lastDate = dayOfMonth
                dailyTotal = 0.0
            }

            dailyTotal += if (transaction.type == GoalInputType.DEPOSIT) {
                transaction.amount
            } else {
                -transaction.amount
            }

            groupedList.add(GoalListItem.GoalTransactionItem(transaction))
        }

        // Add the last date header
        if (lastDate != null) {
            groupedList.add(
                GoalListItem.DateHeader(
                    lastDate,
                    transactions.last().transactionDate.formatToDayOfWeek(),
                    transactions.last().transactionDate.formatToMonthYear(),
                    dailyTotal
                )
            )
        }

        return groupedList
    }
}

data class GoalDetailItem(
    val title: String,
    val saveAmount: Double,
    val remainAmount: Double,
    val amountGoal: Double,
    val goalDate: String,
    val daysLeft: String,
    val transactions: List<GoalListItem>,
    val progress: Int = ((saveAmount / amountGoal) * 100).toInt()
)