package com.example.money_manager_app.utils

import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.entity.Category
import com.example.money_manager_app.data.model.entity.enums.CategoryType

object CategoryUtils {
    val listCategory: List<Category> = listOf(
        Category(1, "Award", 1, R.drawable.income_1, 0, CategoryType.INCOME),
        Category(2, "Allowance", 1, R.drawable.income_2, 0, CategoryType.INCOME),
        Category(3, "Dividend", 1, R.drawable.income_3, 0, CategoryType.INCOME),
        Category(4, "Bonus", 1, R.drawable.income_4, 0, CategoryType.INCOME),
        Category(5, "Investment", 1, R.drawable.income_5, 0, CategoryType.INCOME),
        Category(6, "Lottery", 1, R.drawable.income_6, 0, CategoryType.INCOME),
        Category(7, "Salary", 1, R.drawable.income_7, 0, CategoryType.INCOME),
        Category(8, "Tips", 1, R.drawable.income_8, 0, CategoryType.INCOME),
        Category(9, "Others", 1, R.drawable.income_9, 0, CategoryType.INCOME),
        Category(10, "Groceries", 1, R.drawable.expense_1, 0, CategoryType.EXPENSE),
        Category(11, "Clothing", 1, R.drawable.expense_2, 0, CategoryType.EXPENSE),
        Category(12, "Education", 1, R.drawable.expense_3, 0, CategoryType.EXPENSE),
        Category(13, "Entertainment", 1, R.drawable.expense_4, 0, CategoryType.EXPENSE),
        Category(14, "Fitness", 1, R.drawable.expense_5, 0, CategoryType.EXPENSE),
        Category(15, "Food", 1, R.drawable.expense_6, 0, CategoryType.EXPENSE),
        Category(16, "Furniture", 1, R.drawable.expense_7, 0, CategoryType.EXPENSE),
        Category(17, "Gifts", 1, R.drawable.expense_8, 0, CategoryType.EXPENSE),
        Category(18, "Health", 1, R.drawable.expense_9, 0, CategoryType.EXPENSE),
        Category(19, "Pet", 1, R.drawable.expense_10, 0, CategoryType.EXPENSE),
        Category(20, "Shopping", 1, R.drawable.expense_11, 0, CategoryType.EXPENSE),
        Category(21, "Transportation", 1, R.drawable.expense_12, 0, CategoryType.EXPENSE),
        Category(22, "Travel", 1, R.drawable.expense_13, 0, CategoryType.EXPENSE),
        Category(23, "Other", 1, R.drawable.expense_14, 0, CategoryType.EXPENSE)
    )

}