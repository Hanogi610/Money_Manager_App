package com.example.money_manager_app.fragment.wallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.SubTransaction
import com.example.money_manager_app.data.model.SubTransactionListItem
import com.example.money_manager_app.data.model.entity.DebtTransaction
import com.example.money_manager_app.data.model.entity.GoalTransaction
import com.example.money_manager_app.data.model.entity.Wallet
import com.example.money_manager_app.databinding.DateHeaderItemBinding
import com.example.money_manager_app.databinding.TransactionItemBinding
import com.example.money_manager_app.utils.toFormattedTimeString

class GoalTransactionAdapter(
    private val context: Context,
    private val currencySymbol: String,
    private val wallets: List<Wallet>
) : ListAdapter<SubTransactionListItem, RecyclerView.ViewHolder>(SubTransactionListItemDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_DATE_HEADER = 0
        private const val VIEW_TYPE_TRANSACTION_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SubTransactionListItem.DateHeader -> VIEW_TYPE_DATE_HEADER
            is SubTransactionListItem.GoalTransactionItem -> VIEW_TYPE_TRANSACTION_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_DATE_HEADER) {
            val binding = DateHeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DateHeaderViewHolder(binding)
        } else {
            val binding = TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            TransactionViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is SubTransactionListItem.DateHeader -> (holder as DateHeaderViewHolder).bind(item)
            is SubTransactionListItem.GoalTransactionItem -> (holder as TransactionViewHolder).bind(item.transaction)
        }
    }

    inner class DateHeaderViewHolder(private val binding: DateHeaderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(header: SubTransactionListItem.DateHeader) {
            binding.apply {
                dayOfWeekLabel.text = header.dayOfWeek
                dayOfMonthLabel.text = header.dayOfMonth
                monthYearLabel.text = header.monthYear
                totalAmountLabel.text = context.getString(R.string.money_amount, currencySymbol, header.total)
            }
        }
    }

    inner class TransactionViewHolder(private val binding: TransactionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: SubTransaction) {
            binding.transactionTypeTextView.text = when (transaction) {
                is GoalTransaction -> context.getString(R.string.goal_transaction_name, transaction.type.toString(),transaction.name)
                is DebtTransaction -> context.getString(R.string.debt_transaction_name, transaction.action.toString(), transaction.name)
                else -> ""
            }
            binding.transactionAmount.text = context.getString(R.string.money_amount, currencySymbol, transaction.amount)
            binding.transactionTime.text = transaction.date?.toFormattedTimeString()
            binding.walletName.text = wallets.find { it.id == transaction.walletId }?.name
        }
    }
}

class SubTransactionListItemDiffCallback : DiffUtil.ItemCallback<SubTransactionListItem>() {
    override fun areItemsTheSame(oldItem: SubTransactionListItem, newItem: SubTransactionListItem): Boolean {
        return if (oldItem is SubTransactionListItem.DateHeader && newItem is SubTransactionListItem.DateHeader) {
            oldItem.dayOfMonth == newItem.dayOfMonth && oldItem.monthYear == newItem.monthYear
        } else if (oldItem is SubTransactionListItem.GoalTransactionItem && newItem is SubTransactionListItem.GoalTransactionItem) {
            oldItem.transaction.id == newItem.transaction.id
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItem: SubTransactionListItem, newItem: SubTransactionListItem): Boolean {
        return oldItem == newItem
    }
}