package com.example.money_manager_app.fragment.init_amount

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.money_manager_app.R
import com.example.money_manager_app.activity.MainViewModel
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.databinding.FragmentInnitAccountBinding

class InnitAccountFragment : BaseFragment<FragmentInnitAccountBinding, InnitAccountViewModel>(R.layout.fragment_innit_account) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentInnitAccountBinding? = null

    override fun getVM(): InnitAccountViewModel {
        val viewModel: InnitAccountViewModel by viewModels()
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInnitAccountBinding.inflate(inflater, container, false)

        val currentCurrency = mainViewModel.addingAccount.value.currency
        val currencySymbol = getString(currentCurrency.symbolRes)
        _binding?.etAmount?.hint = getString(R.string.amount_hint, currencySymbol)
        _binding?.apply {
            tvSkip.setOnClickListener {
                // Skip the init amount
                handleAmountConfirmation(0.0)
            }
            btnConfirm.setOnClickListener {
                // Save the init amount
                val initAmount = etAmount.text.toString().toDoubleOrNull() ?: 0.0
                handleAmountConfirmation(initAmount)
            }
        }

        return _binding!!.root
    }

    private fun handleAmountConfirmation(amount: Double){
        mainViewModel.setAddingAccount(
            mainViewModel.addingAccount.value.copy(initAmount = amount)
        )
        mainViewModel.addAccount()
        val navOptions = navOptions {
            popUpTo(R.id.addAccountFragment) { inclusive = true }
        }
        findNavController().navigate(R.id.action_innitAccountFragment_to_homeFragment, null, navOptions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}