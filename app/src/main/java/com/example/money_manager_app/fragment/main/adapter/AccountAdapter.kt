package com.example.money_manager_app.fragment.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.entity.AccountWithWallet
import com.example.money_manager_app.databinding.AccountItemBinding

class AccountAdapter(
    private val context: Context,
    private val currencySymbol: String,
    private val accounts: List<AccountWithWallet>,
    private val currentAccount: AccountWithWallet,
    private val onAccountSelected: (AccountWithWallet) -> Unit
) : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val binding = AccountItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val account = accounts[position]
        holder.bind(account, account.account == currentAccount.account)
    }

    override fun getItemCount(): Int {
        return if (accounts.isNotEmpty()) accounts.size else 0
    }

    inner class AccountViewHolder(private val binding: AccountItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(account: AccountWithWallet, isSelect: Boolean = false) {
            binding.accountName.text = account.account.nameAccount
            binding.accountBalance.text = context.getString(R.string.money_amount, currencySymbol , account.wallets.sumOf { it.amount })
            binding.checkIcon.visibility = if (isSelect) View.VISIBLE else View.GONE

            itemView.setOnClickListener {
                onAccountSelected(account)
            }
        }
    }
}
