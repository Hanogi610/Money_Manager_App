package com.example.money_manager_app.fragment.language

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_manager_app.R
import com.example.money_manager_app.fragment.language.adapter.LanguageAdapter
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.databinding.FragmentLanguageBinding
import com.example.money_manager_app.utils.setOnSafeClickListener
import com.example.money_manager_app.fragment.language.viewmodel.LanguageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguageBinding, LanguageViewModel>(R.layout.fragment_language) {

    private lateinit var adapter: LanguageAdapter

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        getVM().selectLanguage(appPreferences.getLanguage())
    }

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
            appPreferences.setLanguage(getVM().changeLanguage())
            appPreferences.setFirstTimeLaunch(false)
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()

        getVM().languages.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}

object LocaleHelper {
    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}