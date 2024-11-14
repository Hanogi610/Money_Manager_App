package com.example.moneymanager.fragment.currency_selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.money_manager_app.R
import com.example.money_manager_app.activity.MainViewModel
import com.example.money_manager_app.databinding.FragmentCurrencySelectionBinding
import com.example.money_manager_app.model.entity.enums.Currency
import com.example.money_manager_app.utils.getCurrencyName
import com.example.money_manager_app.utils.getCurrencySymbol


class CurrencySelectionFragment : Fragment() {

    private var _binding: FragmentCurrencySelectionBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencySelectionBinding.inflate(inflater, container, false)

        val currencies = Currency.entries.map {
            "${it.name} - ${
                getCurrencyName(
                    requireContext(),
                    it
                )
            }(${getCurrencySymbol(requireContext(), it)})"
        }
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCurrency.adapter = adapter
        binding.btnNext.setOnClickListener {
            val currency = Currency.fromId(binding.spinnerCurrency.selectedItemPosition + 1)
            currency?.let {
                mainViewModel.setAddingAccount(
                    mainViewModel.addingAccount.value.copy(currency = it)
                )
            }
            findNavController().navigate(R.id.action_currencySelectionFragment_to_innitAccountFragment)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}