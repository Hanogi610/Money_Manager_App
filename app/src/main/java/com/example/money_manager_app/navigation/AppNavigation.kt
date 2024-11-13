package com.example.money_manager_app.navigation

import android.os.Bundle
import com.example.money_manager_app.base.navigation.BaseNavigator

interface AppNavigation : BaseNavigator {
    fun openSplashToInnitAccountScreen(bundle: Bundle? = null)
    fun openSplashToLanguageScreen(bundle: Bundle? = null)
    fun openLanguageToInnitAccountScreen(bundle: Bundle? = null)
    fun openInnitAccountToHomeScreen(bundle: Bundle? = null)
    fun openHomeToInnitAccountScreen(bundle: Bundle? = null)
    fun openSplashToHomeScreen(bundle: Bundle? = null)
}