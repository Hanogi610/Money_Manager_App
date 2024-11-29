package com.example.money_manager_app.data.model.entity


class CalendarSummary(var income: Double, var expense: Double) {
    val netIncome: Double
        get() = this.income + this.expense
}