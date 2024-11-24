package com.example.money_manager_app.fragment

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragmentNotRequireViewModel
import com.example.money_manager_app.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class SplashFragment : BaseFragmentNotRequireViewModel<FragmentSplashBinding>(R.layout.fragment_splash) {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        Log.d(TAG, "initView: 1")
        goToNextScreen()
    }

    private fun goToNextScreen() {
        lifecycleScope.launch {
            Log.d(TAG, "goToNextScreen: 2")
            delay(2000)

            val isFirstTime = appPreferences.isFirstTimeLaunch()

            Log.d(TAG, "goToNextScreen: 3")
            if (isFirstTime) {
                Log.d(TAG, "goToNextScreen: 4")
                appNavigation.openSplashToLanguageScreen()
            } else {
                Log.d(TAG, "goToNextScreen: 4.2")
                appNavigation.openSplashToPasswordScreen()
            }
        }
    }

    companion object {
        private const val TAG = "SplashFragment"
    }

}