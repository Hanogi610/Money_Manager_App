package com.example.money_manager_app.navigation

import android.os.Bundle
import com.example.money_manager_app.R
import com.example.money_manager_app.base.navigation.BaseNavigatorImpl
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class AppNavigationImpl@Inject constructor() : BaseNavigatorImpl(),
    AppNavigation {

    override fun openSplashToLanguageScreen(bundle: Bundle?) {
        openScreen(R.id.action_splashScreenFragment_to_languageFragment, bundle)
    }

    override fun openSplashToPasswordScreen(bundle: Bundle?) {
        openScreen(R.id.action_splashScreenFragment_to_passwordFragment, bundle)
    }

    override fun openLanguageToPasswordScreen(bundle: Bundle?) {
        openScreen(R.id.action_languageFragment_to_passwordFragment, bundle)
    }

    override fun openPasswordToCreateAccountScreen(bundle: Bundle?) {
        openScreen(R.id.action_passwordFragment_to_createAccountFragment, bundle)
    }

    override fun openMainScreenToAddDebtScreen(bundle: Bundle?) {
        openScreen(R.id.action_mainFragment_to_addDebtFragment, bundle)
    }

    override fun openMainScreenToDebtDetailScreen(bundle: Bundle?) {
        openScreen(R.id.action_mainFragment_to_debtDetailFragment, bundle)
    }

    override fun openDebDetailToAddDebtTransactionScreen(bundle: Bundle?) {
        openScreen(R.id.action_debtDetailFragment_to_addDebtTransactionFragment, bundle)
    }

    override fun openMainScreenToGoalDetailScreen(bundle: Bundle?) {
        openScreen(R.id.action_mainFragment_to_goalDetailFragment, bundle)
    }
}