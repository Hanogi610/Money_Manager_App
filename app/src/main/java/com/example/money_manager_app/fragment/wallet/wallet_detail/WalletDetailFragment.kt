package com.example.money_manager_app.fragment.wallet.wallet_detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.money_manager_app.R

class WalletDetailFragment : Fragment() {

    companion object {
        fun newInstance() = WalletDetailFragment()
    }

    private val viewModel: WalletDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_wallet_detail, container, false)
    }
}