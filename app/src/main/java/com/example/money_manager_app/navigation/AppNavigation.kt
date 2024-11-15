package com.example.money_manager_app.navigation

import android.os.Bundle
import com.example.money_manager_app.base.navigation.BaseNavigator

interface AppNavigation : BaseNavigator {
    fun openSplashToLanguageScreen(bundle: Bundle? = null)
    fun openSplashToPasswordScreen(bundle: Bundle? = null)
    fun openLanguageToPasswordScreen(bundle: Bundle? = null)
    fun openPasswordToCreateAccountScreen(bundle: Bundle? = null)
}