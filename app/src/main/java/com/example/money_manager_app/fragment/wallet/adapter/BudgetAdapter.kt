package com.example.money_manager_app.fragment.wallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.BudgetDetail
import com.example.money_manager_app.data.model.entity.Debt
import com.example.money_manager_app.databinding.AddNewItemBinding
import com.example.money_manager_app.databinding.BudgetItemBinding
import com.example.money_manager_app.databinding.DebtItemBinding

class BudgetAdapter (
    private val context: Context,
    private val currentCurrencySymbol: String,
    private val onItemClick: (Debt) -> Unit,
    private val onAddNewClick: () -> Unit
) : ListAdapter<BudgetDetail, RecyclerView.ViewHolder>(BudgetDiffCallback()) {

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
            val budgetDetail = getItem(position)
            (holder as DebtViewHolder).bind(budgetDetail)
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

    inner class DebtViewHolder(val binding: I) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(budgetDetail: BudgetDetail){

        }

    }

    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_ADD_NEW = 1
    }
}


class BudgetDiffCallback : DiffUtil.ItemCallback<BudgetDetail>() {


    override fun areItemsTheSame(oldItem: BudgetDetail, newItem: BudgetDetail): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: BudgetDetail, newItem: BudgetDetail): Boolean {
        TODO("Not yet implemented")
    }
}