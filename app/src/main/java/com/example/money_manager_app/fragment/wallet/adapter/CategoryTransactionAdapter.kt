package com.example.money_manager_app.fragment.wallet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.PrimaryKey
import com.example.money_manager_app.data.model.CategoryTransactionDetail
import com.example.money_manager_app.data.model.entity.Transfer
import com.example.money_manager_app.databinding.ItemCategoryTransactionBinding

class CategoryTransactionAdapter(
    private var categoryTransactionList: List<CategoryTransactionDetail>,
    private val currentCurrencySymbol: String,
    private val onTransactionItemClick: (Transfer) -> Unit
) : RecyclerView.Adapter<CategoryTransactionAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCategoryTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.ivCategory.setImageResource(categoryTransactionList[adapterPosition].iconId)
            binding.nameCategory.text = categoryTransactionList[adapterPosition].name
            binding.countCategory.text = categoryTransactionList[adapterPosition].totalCategoryTransaction.toString()
            binding.amountCategory.text = "-${currentCurrencySymbol} + ${categoryTransactionList[adapterPosition].totalAmount}"
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryTransactionAdapter.ViewHolder {
        val binding = ItemCategoryTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryTransactionAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return categoryTransactionList.size
    }

    fun setDate(newList: List<CategoryTransactionDetail>) {
        categoryTransactionList = newList
        notifyDataSetChanged()
    }
}