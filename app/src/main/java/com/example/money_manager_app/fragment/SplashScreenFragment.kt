package com.example.money_manager_app.fragment

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragmentNotRequireViewModel
import com.example.money_manager_app.databinding.FragmentSplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenFragment : BaseFragmentNotRequireViewModel<FragmentSplashScreenBinding>(R.layout.fragment_splash_screen) {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        goToNextScreen()
    }

    private fun goToNextScreen() {
        lifecycleScope.launch {
            delay(200)
            val isFirstTime = appPreferences.getIsFirstTime().firstOrNull() ?: true

            if (isFirstTime) {
                appNavigation.openSplashToLanguageScreen()
            } else {
                appNavigation.openSplashToInnitAccountScreen()
            }
        }
    }

}