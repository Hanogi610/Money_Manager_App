package com.example.money_manager_app.activity

import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.money_manager_app.R
import com.example.money_manager_app.base.activity.BaseActivity
import com.example.money_manager_app.databinding.ActivityMainBinding
import com.example.money_manager_app.navigation.AppNavigation
import com.example.money_manager_app.utils.LanguageStart
import com.example.money_manager_app.utils.checkLanguageInitialization
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var appNavigation: AppNavigation
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun getVM(): MainViewModel {
        val viewModel: MainViewModel by viewModels()
        if (checkLanguageInitialization(this) == LanguageStart.NOT_INITIALIZED) {
            setPreferredLocale("en") // Set default language to English
        }
        setContentView(binding.root)

        viewModel.getAccount()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.accounts.collect {
                    if (it.isNotEmpty()) {
                        viewModel.setCurrentAccount(it[0])
                    }
                }
            }
        }

        // Initialize NavController
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        setDynamicStartDestination()
        return viewModel
    }

    private fun setDynamicStartDestination() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val languageStart = checkLanguageInitialization(this@MainActivity)
                Log.d(TAG, "Checking language start: $languageStart")

                val navGraph = navController.navInflater.inflate(R.navigation.main_navigation)
                val startDestination = when (languageStart) {
                    LanguageStart.NOT_INITIALIZED -> R.id.passcodeFragment
                    LanguageStart.INITIALIZED -> R.id.passcodeFragment
                }
                navGraph.setStartDestination(startDestination)
                navController.graph = navGraph
            }
        }
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    private fun setPreferredLocale(languageCode: String) {
        val localeList = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(localeList)
    }
}