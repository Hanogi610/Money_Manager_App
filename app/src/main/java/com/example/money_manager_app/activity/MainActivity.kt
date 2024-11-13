package com.example.money_manager_app.activity

import androidx.activity.viewModels
import com.example.money_manager_app.R
import com.example.money_manager_app.base.activity.BaseActivity
import com.example.money_manager_app.databinding.ActivityMainBinding
import com.example.money_manager_app.navigation.AppNavigation
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
}