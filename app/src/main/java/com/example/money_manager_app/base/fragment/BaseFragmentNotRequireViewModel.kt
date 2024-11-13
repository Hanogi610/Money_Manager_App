package com.example.money_manager_app.base.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.money_manager_app.navigation.AppNavigation
import com.example.money_manager_app.pref.AppPreferences
import javax.inject.Inject

abstract class BaseFragmentNotRequireViewModel<BD : ViewDataBinding>(@LayoutRes id: Int) :
    Fragment(id) {

    @Inject
    lateinit var appNavigation: AppNavigation

    @Inject
    lateinit var appPreferences: AppPreferences

//    private val adSize: AdSize
//        get() {
//            val displayMetrics = resources.displayMetrics
//            val adWidthPixels =
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                    val windowMetrics: WindowMetrics =
//                        requireActivity().windowManager.currentWindowMetrics
//                    windowMetrics.bounds.width()
//                } else {
//                    displayMetrics.widthPixels
//                }
//            val density = displayMetrics.density
//            val adWidth = (adWidthPixels / density).toInt()
//            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
//                requireContext(),
//                adWidth
//            )
//        }

    private var _binding: BD? = null
    protected val binding: BD
        get() = _binding
            ?: throw IllegalStateException("Cannot access view after view destroyed or before view creation")

//    private var currentNativeAd: NativeAd? = null
//    private var currentBannerAd: AdView? = null
//    private var currentInterstitialAd: InterstitialAd? = null

//    private var lastTimeShowInterstitialAd = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DataBindingUtil.bind(view)
        _binding?.lifecycleOwner = viewLifecycleOwner

        if (savedInstanceState == null) {
            onInit()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            onInit(savedInstanceState)
        }
    }

    private fun onInit(savedInstanceState: Bundle? = null) {
        initData(savedInstanceState)

        initToolbar()

        initView(savedInstanceState)

//        checkNetworkAndRetry()

        setOnBackPress()

        setOnClick()

        bindingStateView()
    }

    private fun setOnBackPress() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    open fun initToolbar() {}

    open fun initData(savedInstanceState: Bundle?) {}

//    open fun showAds(
//        loadingLayout: ShimmerFrameLayout? = null,
//        contentLayout: FrameLayout? = null,
//        adsLayout: RelativeLayout? = null,
//        adsType: AdsType = AdsType.NATIVE_ADS_CARD,
//        isShowOnlyFullAds: Boolean = true,
//        onLoadCompleted: () -> Unit = {}
//    ) {
//        lifecycleScope.launch {
//            val isNormalVersion = BuildConfig.IS_NORMAL_VERSION
//
//            val adsEnable = if (isNormalVersion) appPreferences.getAdsEnable().firstOrNull() ?: true else true
//            val bannerAdsEnable =
//                if (isNormalVersion) appPreferences.getBannerAdsEnable().firstOrNull() ?: true else true
//            val nativeAdsEnable =
//                if (isNormalVersion) appPreferences.getNativeAdsEnable().firstOrNull() ?: true else true
//            val interstitialAdsEnable =
//                if (isNormalVersion) appPreferences.getInterstitialAdsEnable().firstOrNull() ?: true else true
//
//            val minTimeShowAds = appPreferences.getMinTimeShowAds().firstOrNull() ?: 30
//
//            if (!isAdded || !adsEnable || (isNormalVersion && isShowOnlyFullAds)) {
//                onLoadCompleted()
//                return@launch
//            }
//
//            try {
//                when (adsType) {
//                    AdsType.BANNER -> {
//                        if (!bannerAdsEnable) return@launch
//                        showBannerAds(loadingLayout, contentLayout, adsLayout)
//                    }
//
//                    AdsType.INTERSTITIAL -> {
//                        if (!interstitialAdsEnable) return@launch
//
//                        val currentTime = System.currentTimeMillis() / 1000
//
//                        if (isNormalVersion && currentTime - lastTimeShowInterstitialAd < minTimeShowAds) return@launch
//
//                        showInterstitialAd(onLoadCompleted)
//                    }
//
//                    else -> {
//                        if (!nativeAdsEnable) return@launch
//                        showNativeAds(loadingLayout, contentLayout, adsLayout, adsType)
//                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    private fun showInterstitialAd(onLoadCompleted: () -> Unit) {
//        if (AdHolder.preloadedInterstitialAds.isNotEmpty()) {
//            val index = Random.nextInt(AdHolder.preloadedInterstitialAds.size)
//            currentInterstitialAd = AdHolder.preloadedInterstitialAds.removeAt(index)
//            lastTimeShowInterstitialAd = System.currentTimeMillis() / 1000
//            currentInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
//                override fun onAdShowedFullScreenContent() {
//                    super.onAdShowedFullScreenContent()
//                    onLoadCompleted()
//                }
//
//                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
//                    super.onAdFailedToShowFullScreenContent(adError)
//                    onLoadCompleted()
//                }
//            }
//            currentInterstitialAd?.show(requireActivity())
//            loadInterstitialAds()
//        } else {
//            loadInterstitialAds()
//            onLoadCompleted()
//        }
//    }
//
//    private fun showBannerAds(
//        loadingLayout: ShimmerFrameLayout? = null,
//        contentLayout: FrameLayout? = null,
//        adsLayout: RelativeLayout? = null
//    ) {
//        val handler = Handler(Looper.getMainLooper())
//        val startTime = System.currentTimeMillis()
//
//        fun tryLoadBannerAds() {
//            if (AdHolder.preloadedBannerAds.isNotEmpty()) {
//                loadingLayout?.stopShimmer()
//                loadingLayout?.isVisible = false
//                handler.removeCallbacksAndMessages(null)
//                val bannerAd = AdHolder.preloadedBannerAds.removeAt(Random.nextInt(AdHolder.preloadedBannerAds.size))
//                currentBannerAd = bannerAd
//                contentLayout?.removeAllViews()
//                contentLayout?.addView(bannerAd)
//                loadBannerAds()
//            } else if (System.currentTimeMillis() - startTime < 6000) {
//                handler.postDelayed({ loadBannerAds(loadingLayout); tryLoadBannerAds() }, 1000)
//            } else {
//                loadingLayout?.stopShimmer()
//                adsLayout?.isVisible = false
//            }
//        }
//
//        tryLoadBannerAds()
//    }
//
//    private fun showNativeAds(
//        loadingLayout: ShimmerFrameLayout? = null,
//        contentLayout: FrameLayout? = null,
//        adsLayout: RelativeLayout? = null,
//        adsType: AdsType
//    ) {
//        val handler = Handler(Looper.getMainLooper())
//        val startTime = System.currentTimeMillis()
//
//        fun tryLoadNativeAds() {
//            if (AdHolder.preloadedNativeAds.isNotEmpty()) {
//                loadingLayout?.stopShimmer()
//                loadingLayout?.isVisible = false
//                handler.removeCallbacksAndMessages(null)
//                val index = Random.nextInt(AdHolder.preloadedNativeAds.size)
//                val nativeAd = AdHolder.preloadedNativeAds.removeAt(index)
//                val nativeAdBinding = createNativeAdBinding(nativeAd, adsType)
//                currentNativeAd = nativeAd
//                contentLayout?.removeAllViews()
//                contentLayout?.addView(nativeAdBinding?.root)
//                loadNativeAds()
//            } else if (System.currentTimeMillis() - startTime < 6000) {
//                handler.postDelayed({ loadNativeAds(loadingLayout); tryLoadNativeAds() }, 1000)
//            } else {
//                loadingLayout?.stopShimmer()
//                adsLayout?.isVisible = false
//            }
//        }
//
//        tryLoadNativeAds()
//    }
//
//    private fun loadNativeAds(loadingLayout: ShimmerFrameLayout? = null) {
//        loadingLayout?.startShimmer()
//        val adLoader = Builder(requireContext(), ADS_UNIT_NATIVE_ID)
//            .forNativeAd { ad: NativeAd ->
//                AdHolder.preloadedNativeAds.add(ad)
//                loadingLayout?.stopShimmer()
//            }
//            .withAdListener(object : AdListener() {
//                override fun onAdFailedToLoad(adError: LoadAdError) {
//                    loadingLayout?.stopShimmer()
//                    loadingLayout?.isVisible = false
//                }
//            }).build()
//        adLoader.loadAd(AdRequest.Builder().build())
//    }
//
//    private fun loadBannerAds(loadingLayout: ShimmerFrameLayout? = null) {
//        loadingLayout?.startShimmer()
//        val adView = AdView(requireContext())
//        adView.adUnitId = ADS_UNIT_BANNER_ID
//        adView.setAdSize(adSize)
//        adView.loadAd(AdRequest.Builder().build())
//        adView.adListener = object : AdListener() {
//            override fun onAdLoaded() {
//                AdHolder.preloadedBannerAds.add(adView)
//                loadingLayout?.stopShimmer()
//            }
//
//            override fun onAdFailedToLoad(p0: LoadAdError) {
//                super.onAdFailedToLoad(p0)
//                loadingLayout?.stopShimmer()
//                loadingLayout?.isVisible = false
//            }
//        }
//    }
//
//    private fun loadInterstitialAds() {
//        val adRequest = AdRequest.Builder().build()
//        InterstitialAd.load(requireContext(),
//            VoiceChangerApplication.ADS_UNIT_INTERSTITIAL_ID,
//            adRequest,
//            object : InterstitialAdLoadCallback() {
//                override fun onAdFailedToLoad(adError: LoadAdError) {
//                }
//
//                override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                    AdHolder.preloadedInterstitialAds.add(interstitialAd)
//                }
//            })
//    }

//    private fun showTimeoutDialog() {
//        val confirmDialog = ConfirmDialog(
//            title = "Connection Timeout",
//            content = "Do you want to reconnect or exit?",
//            negative = "Exit",
//            positive = "Reconnect",
//            onNegative = { requireActivity().finish() },
//            onPositive = { checkNetworkAndRetry() }
//        )
//        confirmDialog.show(parentFragmentManager, "ConfirmDialog")
//    }
//
//    private fun checkNetworkAndRetry() {
//        if (!isNetworkAvailable()) {
//            showTimeoutDialog()
//        }
//    }
//
//    private fun isNetworkAvailable(): Boolean {
//        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val network = connectivityManager.activeNetwork ?: return false
//        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
//        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//    }
//
//    private fun createNativeAdBinding(ad: NativeAd, adsType: AdsType): ViewDataBinding? {
//        return when (adsType) {
//            AdsType.NATIVE_ADS_BOTTOM -> {
//                LayoutNativeAdBottomBinding.inflate(layoutInflater).also {
//                    populateNativeAdBottomView(ad, it)
//                }
//            }
//
//            AdsType.NATIVE_ADS_MIDDLE -> {
//                LayoutNativeAdBottomBinding.inflate(layoutInflater).also {
//                    populateNativeAdBottomView(ad, it, false)
//                }
//            }
//
//            AdsType.NATIVE_ADS_CARD -> {
//                LayoutNativeAdCardBinding.inflate(layoutInflater).also {
//                    populateNativeAdCardView(ad, it)
//                }
//            }
//
//            else -> null
//        }
//    }
//
//    open fun stopNativeAd() {
//        currentNativeAd?.destroy()
//    }
//
//    open fun stopBannerAd() {
//        currentBannerAd?.destroy()
//    }

    open fun onBack() {}

    open fun setOnClick() {}

    open fun initView(savedInstanceState: Bundle?) {}

    open fun bindingStateView() {
        //do nothing
    }

    override fun onDestroyView() {
        _binding?.unbind()
        _binding = null
        super.onDestroyView()
    }

//    fun showHideLoading(isShow: Boolean) {
//        if (activity != null && activity is BaseActivityNotRequireViewModel<*>) {
//            if (isShow) {
//                (activity as BaseActivityNotRequireViewModel<*>?)!!.showLoading()
//            } else {
//                (activity as BaseActivityNotRequireViewModel<*>?)!!.hiddenLoading()
//            }
//        }
//    }
//
//    override fun onPause() {
//        currentBannerAd?.pause()
//        super.onPause()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        currentBannerAd?.resume()
//    }
//
//    override fun onDestroy() {
//        currentBannerAd?.destroy()
//        currentNativeAd?.destroy()
//        super.onDestroy()
//    }

//    private fun populateNativeAdBottomView(
//        nativeAd: NativeAd,
//        nativeAdBinding: LayoutNativeAdBottomBinding,
//        isShowVideo: Boolean = true
//    ) {
//        val nativeAdView = nativeAdBinding.nativeAdView
//
//        nativeAdView.callToActionView = nativeAdBinding.cta
//        nativeAdView.headlineView = nativeAdBinding.primary
//        nativeAdView.mediaView = nativeAdBinding.mediaView
//        nativeAdView.bodyView = nativeAdBinding.body
//        nativeAdView.iconView = nativeAdBinding.icon
//
//        nativeAdBinding.primary.text = nativeAd.headline
//
//        if (isShowVideo) {
//            nativeAdBinding.mediaView.isVisible = true
//            nativeAd.mediaContent?.videoController?.let {
//                nativeAdBinding.mediaView.mediaContent = nativeAd.mediaContent
//            }
//        } else {
//            nativeAdBinding.mediaView.isVisible = false
//        }
//
//        if (nativeAd.body.isNullOrEmpty()) {
//            nativeAdBinding.body.visibility = View.GONE
//        } else {
//            nativeAdBinding.body.visibility = View.VISIBLE
//            nativeAdBinding.body.text = nativeAd.body
//        }
//
//        if (nativeAd.icon == null) {
//            nativeAdBinding.icon.visibility = View.GONE
//        } else {
//            nativeAdBinding.icon.visibility = View.VISIBLE
//            nativeAdBinding.icon.setImageDrawable(nativeAd.icon?.drawable)
//        }
//
//        if (nativeAd.callToAction.isNullOrEmpty()) {
//            nativeAdBinding.cta.visibility = View.GONE
//        } else {
//            nativeAdBinding.cta.visibility = View.VISIBLE
//            nativeAdBinding.cta.text = nativeAd.callToAction
//        }
//
//        nativeAdView.setNativeAd(nativeAd)
//    }

//    private fun populateNativeAdCardView(
//        nativeAd: NativeAd,
//        nativeAdBinding: LayoutNativeAdCardBinding
//    ) {
//        val nativeAdView = nativeAdBinding.nativeAdView
//
//        nativeAdView.callToActionView = nativeAdBinding.cta
//        nativeAdView.headlineView = nativeAdBinding.primary
//        nativeAdView.bodyView = nativeAdBinding.body
//        nativeAdView.iconView = nativeAdBinding.icon
//
//        nativeAdBinding.primary.text = nativeAd.headline
//
//        if (nativeAd.body.isNullOrEmpty()) {
//            nativeAdBinding.body.visibility = View.GONE
//        } else {
//            nativeAdBinding.body.visibility = View.VISIBLE
//            nativeAdBinding.body.text = nativeAd.body
//        }
//
//        if (nativeAd.icon == null) {
//            nativeAdBinding.icon.visibility = View.GONE
//        } else {
//            nativeAdBinding.icon.visibility = View.VISIBLE
//            nativeAdBinding.icon.setImageDrawable(nativeAd.icon?.drawable)
//        }
//
//        if (nativeAd.callToAction.isNullOrEmpty()) {
//            nativeAdBinding.cta.visibility = View.GONE
//        } else {
//            nativeAdBinding.cta.visibility = View.VISIBLE
//            nativeAdBinding.cta.text = nativeAd.callToAction
//        }
//
//        nativeAdView.setNativeAd(nativeAd)
//    }
}

//object AdHolder {
//    val preloadedNativeAds = mutableListOf<NativeAd>()
//    val preloadedBannerAds = mutableListOf<AdView>()
//    val preloadedInterstitialAds = mutableListOf<InterstitialAd>()
//}