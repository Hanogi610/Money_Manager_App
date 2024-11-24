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
    fun openDebDetailToAddDebtTransactionScreen(bundle: Bundle? = null)
    fun openMainScreenToGoalDetailScreen(bundle: Bundle? = null)
    fun openGoalDetailToAddGoalTransactionScreen(bundle: Bundle? = null)
    fun openGoalDetailToAddGoalScreen(bundle: Bundle? = null)
}