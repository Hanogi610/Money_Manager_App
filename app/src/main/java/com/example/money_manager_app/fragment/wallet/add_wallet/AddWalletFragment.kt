package com.example.money_manager_app.fragment.wallet.add_wallet

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moneymanager.databinding.FragmentAddWalletBinding

class AddWalletFragment : Fragment() {

    private var _binding: FragmentAddWalletBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddWalletViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddWalletBinding.inflate(inflater, container, false)



        return binding.root
    }
}