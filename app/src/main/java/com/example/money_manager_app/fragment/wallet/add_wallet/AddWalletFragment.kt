package com.example.money_manager_app.fragment.wallet.add_wallet

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.databinding.FragmentAddWalletBinding

class AddWalletFragment :
    BaseFragment<FragmentAddWalletBinding, AddWalletViewModel>(R.layout.fragment_add_wallet) {

    override fun getVM(): AddWalletViewModel {
        val viewModel: AddWalletViewModel by viewModels()
        return viewModel
    }

}