package com.example.money_manager_app.fragment

import android.content.Context
import android.content.res.Configuration
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_manager_app.R
import com.example.money_manager_app.adapter.LanguageAdapter
import com.example.money_manager_app.adapter.LanguageAdapter.Companion.VIEW_TYPE_2
import com.example.money_manager_app.base.fragment.BaseFragment
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.Locale
import com.example.money_manager_app.databinding.FragmentLanguageBinding
import com.example.money_manager_app.static_data.LanguageDataSource
import com.example.money_manager_app.utils.setOnSafeClickListener

class LanguageFragment : BaseFragment<FragmentLanguageBinding, LanguageViewModel>(R.layout.fragment_language) {

    private lateinit var adapter: LanguageAdapter
    private val languages = LanguageDataSource.getLanguageList()
    private lateinit var language: String
    private var directory: String? = null

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        arguments?.let {
//            directory = it.getString(DIRECTORY)
        }

        lifecycleScope.launch {
            val languageName = appPreferences.getLanguage().firstOrNull()
            languageName?.let { name ->
                languages.forEach { language ->
                    language.isCheck = language.locale == name
                }
            }
            language = languageName ?: "en"
        }
    }

    override fun getVM(): LanguageViewModel {
        val viewModel: LanguageViewModel by viewModels()
        return viewModel
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        adapter = LanguageAdapter(languages, VIEW_TYPE_2) {
            language = it.locale
            getVM().selectLanguage()
        }

        binding.rvLanguage.layoutManager = LinearLayoutManager(context)
        binding.rvLanguage.adapter = adapter

//        showAds(
//            loadingLayout = binding.bannerAds.loadingView,
//            contentLayout = binding.bannerAds.adsContainer,
//            adsLayout = binding.bannerAds.nativeAdContainer,
//            adsType = AdsType.NATIVE_ADS_BOTTOM,
//            isShowOnlyFullAds = false
//        )
    }

    override fun bindingStateView() {
        super.bindingStateView()

//        getVM().isSelectLanguage.observe(this) {
//            if (it) {
//                showAds(
//                    loadingLayout = binding.bannerAds.loadingView,
//                    contentLayout = binding.bannerAds.adsContainer,
//                    adsLayout = binding.bannerAds.nativeAdContainer,
//                    adsType = AdsType.NATIVE_ADS_BOTTOM,
//                    isShowOnlyFullAds = false
//                )
//            }
//        }
    }

    override fun setOnClick() {
        super.setOnClick()

        binding.ivDone.setOnSafeClickListener {
            LocaleHelper.setLocale(requireContext(), language)

            lifecycleScope.launch {
                appPreferences.setLanguage(language)
                appPreferences.setIsFirstTime(false)
            }

//            if (directory == null) {
//                appNavigation.openLanguageToIntroduceScreen()
//            } else {
//                appNavigation.openLanguageToHomeScreen()
//            }
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