package com.example.money_manager_app.fragment.language.view

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragmentNotRequireViewModel
import com.example.money_manager_app.databinding.FragmentLanguageBinding
import com.example.money_manager_app.datasource.LanguageDataSource
import com.example.money_manager_app.fragment.language.adapter.LanguageAdapter
import com.example.money_manager_app.utils.setOnSafeClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class LanguageFragment : BaseFragmentNotRequireViewModel<FragmentLanguageBinding>(R.layout.fragment_language) {

    private lateinit var adapter: LanguageAdapter
    private val languages = LanguageDataSource.getLanguageList()
    private lateinit var language: String

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        val languageName = appPreferences.getLanguage()
        languages.forEach { language ->
            language.isCheck = language.locale == languageName
        }
        language = languageName
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        adapter = LanguageAdapter(languages) {
            language = it.locale
        }

        binding.rvLanguage.layoutManager = LinearLayoutManager(context)
        binding.rvLanguage.adapter = adapter
    }

    override fun setOnClick() {
        super.setOnClick()

        binding.tvDone.setOnSafeClickListener {
            LocaleHelper.setLocale(requireContext(), language)

            lifecycleScope.launch {
                appPreferences.setLanguage(language)
                appPreferences.setFirstTimeLaunch(false)
            }

            appNavigation.openLanguageToPasswordScreen()

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