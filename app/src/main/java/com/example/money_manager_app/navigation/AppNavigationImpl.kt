package com.example.money_manager_app.navigation

import android.os.Bundle
import android.util.Log
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
        Log.d(TAG, "openSplashToPasswordScreen: 1")
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

    override fun openDebtDetailToAddDebtTransactionScreen(bundle: Bundle?) {
        openScreen(R.id.action_debtDetailFragment_to_addDebtTransactionFragment, bundle)
    }

    override fun openMainScreenToGoalDetailScreen(bundle: Bundle?) {
        openScreen(R.id.action_mainFragment_to_goalDetailFragment, bundle)
    }

    override fun openGoalDetailToAddGoalTransactionScreen(bundle: Bundle?) {
        openScreen(R.id.action_goalDetailFragment_to_addGoalTransactionFragment, bundle)
    }

    override fun openMainScreenToAddGoalScreen(bundle: Bundle?) {
        openScreen(R.id.action_mainFragment_to_addGoalFragment, bundle)
    }

    override fun openMainScreenToCreateAccountScreen(bundle: Bundle?) {
        openScreen(R.id.action_mainFragment_to_createAccountFragment, bundle)
    }

    override fun openGoalDetailToAddGoalScreen(bundle: Bundle?) {
        openScreen(R.id.action_goalDetailFragment_to_addGoalFragment, bundle)
    }

    override fun openDebtDetailToAddDebtScreen(bundle: Bundle?) {
        openScreen(R.id.action_debtDetailFragment_to_addDebtFragment, bundle)
    }

    companion object {
        private const val TAG = "AppNavigationImpl"
    }
}