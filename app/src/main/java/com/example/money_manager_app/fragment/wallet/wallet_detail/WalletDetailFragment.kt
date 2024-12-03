package com.example.money_manager_app.fragment.wallet.wallet_detail

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.data.model.entity.Wallet
import com.example.money_manager_app.databinding.FragmentWalletDetailBinding
import com.example.money_manager_app.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletDetailFragment :
    BaseFragment<FragmentWalletDetailBinding, WalletDetailViewModel>(R.layout.fragment_wallet_detail) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var wallet: Wallet? = null

    override fun getVM(): WalletDetailViewModel {
        val viewModel: WalletDetailViewModel by viewModels()
        return viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        arguments?.let {
            wallet = it.getParcelable("wallet")
        }
    }


}