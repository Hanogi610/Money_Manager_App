package com.example.money_manager_app.navigation

import android.os.Bundle
import com.example.money_manager_app.base.navigation.BaseNavigator

interface AppNavigation : BaseNavigator {
    fun openSplashToLanguageScreen(bundle: Bundle? = null)
    fun openSplashToPasswordScreen(bundle: Bundle? = null)
    fun openLanguageToPasswordScreen(bundle: Bundle? = null)
    fun openPasswordToCreateAccountScreen(bundle: Bundle? = null)
    fun openMainScreenToAddDebtScreen(bundle: Bundle? = null)
    fun openMainScreenToDebtDetailScreen(bundle: Bundle? = null)
    fun openDebtDetailToAddDebtTransactionScreen(bundle: Bundle? = null)
    fun openMainScreenToGoalDetailScreen(bundle: Bundle? = null)
    fun openGoalDetailToAddGoalTransactionScreen(bundle: Bundle? = null)
    fun openMainScreenToAddGoalScreen(bundle: Bundle? = null)
    fun openMainScreenToCreateAccountScreen(bundle: Bundle? = null)
    fun openGoalDetailToAddGoalScreen(bundle: Bundle? = null)
    fun openDebtDetailToAddDebtScreen(bundle: Bundle? = null)
    fun openCreateAccountToMainScreen(bundle: Bundle? = null)
    fun openPasswordToMainScreen(bundle: Bundle? = null)
    fun openMainScreenToAddWalletScreen(bundle: Bundle? = null)
    fun openMainScreenToWalletDetailScreen(bundle: Bundle? = null)
    fun openWalletDetailToAddWalletScreen(bundle: Bundle? = null)
    fun openMainScreenToAddFragmentScreen(bundle: Bundle? = null)
    fun openMainScreenToAddBudgetScreen(bundle: Bundle? = null)
    fun openAddBudgetToSelectCategory(bundle: Bundle? = null)
    fun openMainScreenToBudgetDetailScreen(bundle: Bundle? = null)
    fun openBudgetDetailToAddBudgetScreen(bundle: Bundle? = null)
    fun openBudgetDetailToEntertainmentScreen(bundle: Bundle? = null)
    fun openEntertainmentToRecord(bundle: Bundle? = null)
    fun openAddBudgetToWalletScreen(bundle: Bundle? = null)

    fun openWalletDetailScreenToStatisticScreen(bundle: Bundle? = null)
    fun openWalletDetailScreenToAddFragmentScreen(bundle: Bundle? = null)
    fun openStatisticScreenToStructureScreen(bundle: Bundle? = null)
    fun openStatisticScreenToTransactionScreen(bundle: Bundle? = null)
}