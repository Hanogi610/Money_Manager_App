package com.example.money_manager_app.fragment.main.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_manager_app.R
import com.example.money_manager_app.base.BaseBottomSheet
import com.example.money_manager_app.data.model.entity.AccountWithWallet
import com.example.money_manager_app.databinding.AccountSelectionBottomSheetBinding
import com.example.money_manager_app.fragment.main.adapter.AccountAdapter
import com.example.money_manager_app.viewmodel.MainViewModel

class AccountSelectorBottomSheet(
    private val accounts: List<AccountWithWallet>,
    private val currentAccount: AccountWithWallet,
    private val onAccountSelected: (AccountWithWallet) -> Unit,
    private val onAddAccount: () -> Unit
) : BaseBottomSheet<AccountSelectionBottomSheetBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.account_selection_bottom_sheet
    }

    private lateinit var accountAdapter: AccountAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountAdapter = AccountAdapter(
            requireContext(),
            accounts,
            currentAccount
        ) { account ->
            onAccountSelected(account)
            dismiss()
        }

        binding.recyclerViewAccounts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = accountAdapter
        }

        binding.addAccount.setOnClickListener {
            onAddAccount()
            dismiss()
        }
    }
}
