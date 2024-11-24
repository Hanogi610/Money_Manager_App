package com.example.money_manager_app.fragment.language.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_manager_app.R
import com.example.money_manager_app.fragment.language.adapter.LanguageAdapter
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.databinding.FragmentLanguageBinding
import com.example.money_manager_app.utils.setOnSafeClickListener
import com.example.money_manager_app.fragment.language.viewmodel.LanguageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguageBinding, LanguageViewModel>(R.layout.fragment_language) {

    private lateinit var adapter: LanguageAdapter

    override fun getVM(): LanguageViewModel {
        val viewModel: LanguageViewModel by viewModels()
        return viewModel
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        adapter = LanguageAdapter {
            getVM().selectLanguage(it.locale)
        }

        binding.rvLanguage.layoutManager = LinearLayoutManager(context)
        binding.rvLanguage.adapter = adapter
    }

    override fun setOnClick() {
        super.setOnClick()

        binding.tvDone.setOnSafeClickListener {
            getVM().changeLanguage()
            Log.d(TAG, "setOnClick: 1")
            appNavigation.openSplashToPasswordScreen()
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()

        getVM().languages.observe(viewLifecycleOwner) {
            Log.d(TAG, "bindingStateView: 1")
            adapter.submitList(it)
        }
    }

    companion object {
        private const val TAG = "LanguageFragment"
    }
}