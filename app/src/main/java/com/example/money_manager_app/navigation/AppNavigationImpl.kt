package com.example.money_manager_app.navigation

import android.os.Bundle
import com.example.money_manager_app.R
import com.example.money_manager_app.base.navigation.BaseNavigatorImpl
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class AppNavigationImpl@Inject constructor() : BaseNavigatorImpl(),
    AppNavigation {
    override fun openSplashToInnitAccountScreen(bundle: Bundle?) {
        openScreen(R.id.action_splashScreenFragment_to_languageFragment, bundle)
    }

    override fun openSplashToLanguageScreen(bundle: Bundle?) {
        openScreen(R.id.action_splashScreenFragment_to_languageFragment, bundle)
    }

    override fun openLanguageToInnitAccountScreen(bundle: Bundle?) {
        openScreen(R.id.action_languageFragment_to_passcodeFragment, bundle)
    }

    override fun openInnitAccountToHomeScreen(bundle: Bundle?) {
        openScreen(R.id.action_innitAccountFragment_to_homeFragment, bundle)
    }

    override fun openHomeToInnitAccountScreen(bundle: Bundle?) {
        openScreen(R.id.action_homeFragment_to_addAccountFragment, bundle)
    }

    override fun openSplashToHomeScreen(bundle: Bundle?) {
        TODO("Not yet implemented")
    }


}