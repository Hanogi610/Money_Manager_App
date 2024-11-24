package com.example.money_manager_app.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.money_manager_app.R
import com.example.money_manager_app.base.activity.BaseActivity
import com.example.money_manager_app.databinding.ActivityMainBinding
import com.example.money_manager_app.fragment.create_account.viewmodel.CreateAccountViewModel
import com.example.money_manager_app.navigation.AppNavigation
import com.example.money_manager_app.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var appNavigation: AppNavigation

    override fun getVM(): MainViewModel {
        val viewModel: MainViewModel by viewModels()
        return viewModel
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        appNavigation.bind(navController)
    }
    
    companion object {
        private const val TAG = "MainActivity"
    }
}