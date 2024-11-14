package com.example.money_manager_app.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragmentNotRequireViewModel
import com.example.money_manager_app.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashFragment : BaseFragmentNotRequireViewModel<FragmentSplashBinding>(R.layout.fragment_splash) {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        goToNextScreen()
    }

    private fun goToNextScreen() {
        lifecycleScope.launch {
            delay(2000)

            val isFirstTime = appPreferences.isFirstTimeLaunch()

            if (isFirstTime) {
                appNavigation.openSplashToLanguageScreen()
            } else {
                appNavigation.openSplashToInnitAccountScreen()
            }
        }
    }

}