package com.example.money_manager_app.fragment.create_account.view

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.money_manager_app.R
import com.example.money_manager_app.fragment.create_account.viewmodel.CreateAccountViewModel
import com.example.money_manager_app.base.fragment.BaseFragmentNotRequireViewModel
import com.example.money_manager_app.databinding.FragmentCreateAccountBinding
import com.example.money_manager_app.fragment.create_account.adapter.CreateAccountPagerAdapter
import com.example.money_manager_app.utils.setOnSafeClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountFragment :
    BaseFragmentNotRequireViewModel<FragmentCreateAccountBinding>(R.layout.fragment_create_account) {

    private lateinit var adapter: CreateAccountPagerAdapter
    private val createAccountViewModel: CreateAccountViewModel by activityViewModels()

    private val fragments = listOf(
        CreateAccountDetailFragment.newInstance(ARG_ADD_ACCOUNT),
        CreateAccountDetailFragment.newInstance(ARG_SELECT_CURRENCY),
        CreateAccountDetailFragment.newInstance(ARG_INIT_AMOUNT)
    )

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        adapter = CreateAccountPagerAdapter(this, fragments)
        binding.viewPager.adapter = adapter
    }

    override fun bindingStateView() {
        super.bindingStateView()

        createAccountViewModel.currentPage.observe(viewLifecycleOwner) {
            binding.ivBack.isVisible = it != 0

            when (it) {
                0 -> binding.viewPager.currentItem = 0
                1 -> binding.viewPager.currentItem = 1
                2 -> binding.viewPager.currentItem = 2
                else -> {
                    // TODO: Go to next page
                }
            }
        }
    }

    override fun setOnClick() {
        super.setOnClick()

        binding.ivBack.setOnSafeClickListener {
            createAccountViewModel.backPage()
        }
    }

    companion object {
        const val ARG_ADD_ACCOUNT = 0
        const val ARG_SELECT_CURRENCY = 1
        const val ARG_INIT_AMOUNT = 2
    }

}