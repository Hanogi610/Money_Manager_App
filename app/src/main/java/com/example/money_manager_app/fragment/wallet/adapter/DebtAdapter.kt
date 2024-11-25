package com.example.money_manager_app.fragment.wallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.entity.Debt
import com.example.money_manager_app.data.model.entity.DebtDetail
import com.example.money_manager_app.data.model.entity.enums.DebtActionType
import com.example.money_manager_app.data.model.entity.enums.DebtType
import com.example.money_manager_app.databinding.AddNewItemBinding
import com.example.money_manager_app.databinding.DebtItemBinding

class DebtAdapter(
    private val context: Context,
    private val currentCurrencySymbol: String,
    private val onItemClick: (Debt) -> Unit,
    private val onAddNewClick: () -> Unit
) : ListAdapter<DebtDetail, RecyclerView.ViewHolder>(DebtDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return if (position == currentList.size) TYPE_ADD_NEW else TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_ITEM) {
            DebtViewHolder(DebtItemBinding.inflate(inflater, parent, false))
        } else {
            AddDebtViewHolder(AddNewItemBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ITEM) {
            val debt = getItem(position)
            (holder as DebtViewHolder).bind(debt)
        } else if (holder is AddDebtViewHolder) {
            // No binding required for the "Add Debt" button
        }
    }

    override fun getItemCount(): Int {
        return currentList.size + 1
    }

    inner class AddDebtViewHolder(val binding: AddNewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.addName.text = context.getString(R.string.add_debt)
            binding.root.setOnClickListener { onAddNewClick() }
        }
    }

    inner class DebtViewHolder(val binding: DebtItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(debt: DebtDetail) {
            binding.nameLabel.text = if (debt.debt.type == DebtType.PAYABLE) context.getString(
                R.string.i_owe_s, debt.debt.name
            ) else context.getString(R.string.i_lend_s, debt.debt.name)
            binding.detailLabel.text = debt.debt.description
            val currentAmount =
                debt.debt.amount - debt.transactions.filter { it.action == DebtActionType.REPAYMENT }
                    .sumOf { it.amount } + debt.transactions.filter { it.action == DebtActionType.INTEREST || it.action == DebtActionType.DEBT_INCREASE }
                    .sumOf { it.amount }
            binding.amountLabel.text =
                context.getString(R.string.money_amount, currentCurrencySymbol, currentAmount)
            binding.circleLabel.setImageResource(debt.debt.iconId)
            binding.root.setOnClickListener { onItemClick(debt.debt) }
        }
    }

    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_ADD_NEW = 1
    }
}


class DebtDiffCallback : DiffUtil.ItemCallback<DebtDetail>() {
    override fun areItemsTheSame(oldItem: DebtDetail, newItem: DebtDetail): Boolean {
        return oldItem.debt.id == newItem.debt.id
    }

    override fun areContentsTheSame(oldItem: DebtDetail, newItem: DebtDetail): Boolean {
        return oldItem == newItem
    }
}