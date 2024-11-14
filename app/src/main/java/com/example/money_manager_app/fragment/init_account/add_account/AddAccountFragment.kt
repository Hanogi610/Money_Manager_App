package com.example.money_manager_app.fragment.init_account.add_account

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragmentNotRequireViewModel
import com.example.money_manager_app.data.model.entity.enums.Currency
import com.example.money_manager_app.databinding.FragmentAddAccountBinding
import com.example.money_manager_app.fragment.init_account.InitAccountViewModel
import com.example.money_manager_app.utils.getCurrencyName
import com.example.money_manager_app.utils.getCurrencySymbol
import com.example.money_manager_app.utils.setOnSafeClickListener

class AddAccountFragment :
    BaseFragmentNotRequireViewModel<FragmentAddAccountBinding>(R.layout.fragment_add_account) {

    private val viewModel: InitAccountViewModel by viewModels({ requireParentFragment() })

    companion object {
        const val ARG_ADD_ACCOUNT = 0
        const val ARG_SELECT_CURRENCY = 1
        const val ARG_INIT_AMOUNT = 2
        private const val ARG_VIEW_TYPE = "ARG_VIEW_TYPE"

        fun newInstance(viewType: Int): AddAccountFragment {
            val fragment = AddAccountFragment()
            val args = Bundle()
            args.putInt(ARG_VIEW_TYPE, viewType)
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        val viewType = arguments?.getInt(ARG_VIEW_TYPE)
        when (viewType) {
            0 -> {
                binding.tvTitle.text = getString(R.string.add_account)
                binding.tvSubtitle.text = getString(R.string.choose_a_name_for_your_account)
                binding.etAccountName.visibility = View.VISIBLE
                binding.btnNext.setOnSafeClickListener {
                    viewModel.setName(binding.etAmount.text.toString())
                }
            }

            1 -> {
                binding.spinnerCurrency.visibility = View.VISIBLE
                binding.tvTitle.text = getString(R.string.select_currency)
                binding.tvSubtitle.text = getString(R.string.favorite_currency_prompt)
                binding.spinnerCurrency.setOnSafeClickListener {
                    val currency = Currency.fromId(binding.spinnerCurrency.selectedItemPosition + 1)
                    currency?.let {
                        viewModel.setCurrency(currency)
                    }
                }
            }

            2 -> {
                binding.etAmount.visibility = View.VISIBLE
                binding.tvSkip.visibility = View.VISIBLE
                binding.tvTitle.text = getString(R.string.initial_amount)
                binding.tvSubtitle.text = getString(R.string.cash_wallet_prompt)
                binding.btnNext.text = getString(R.string.done)
                val currencies = Currency.entries.map {
                    "${it.name} - ${
                        getCurrencyName(
                            requireContext(), it
                        )
                    }(${getCurrencySymbol(requireContext(), it)})"
                }
                val adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, currencies)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerCurrency.adapter = adapter
                binding.btnNext.setOnSafeClickListener {
                    viewModel.setInitAmount(binding.etAmount.text.toString().toDouble())
                    viewModel.addAccount()
                }
            }
        }
    }

}