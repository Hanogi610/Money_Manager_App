package com.example.money_manager_app.fragment.init_account

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.money_manager_app.R
import com.example.money_manager_app.activity.MainViewModel
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.databinding.FragmentInitAccountBinding
import com.example.money_manager_app.fragment.init_account.adapter.InitAccountPagerAdapter
import com.example.money_manager_app.fragment.init_account.add_account.AddAccountFragment
import com.example.money_manager_app.utils.setOnSafeClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitAccountFragment :
    BaseFragment<FragmentInitAccountBinding, InitAccountViewModel>(R.layout.fragment_init_account) {

    private lateinit var adapter: InitAccountPagerAdapter
    private val mainViewModel: MainViewModel by activityViewModels()

    private val fragments = listOf(
        AddAccountFragment.newInstance(AddAccountFragment.ARG_ADD_ACCOUNT),
        AddAccountFragment.newInstance(AddAccountFragment.ARG_SELECT_CURRENCY),
        AddAccountFragment.newInstance(AddAccountFragment.ARG_INIT_AMOUNT)
    )
    override fun getVM(): InitAccountViewModel {
        val viewModel: InitAccountViewModel by viewModels()
        return viewModel
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        adapter = InitAccountPagerAdapter(this, fragments)
        binding.viewPager.adapter = adapter
    }

}