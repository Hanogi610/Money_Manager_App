package com.example.money_manager_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.Transaction
import com.example.money_manager_app.data.model.TransactionListItem
import com.example.money_manager_app.data.model.entity.DebtTransaction
import com.example.money_manager_app.data.model.entity.GoalTransaction
import com.example.money_manager_app.data.model.entity.Wallet
import com.example.money_manager_app.data.model.entity.enums.DebtActionType
import com.example.money_manager_app.data.model.entity.enums.GoalInputType
import com.example.money_manager_app.databinding.DateHeaderItemBinding
import com.example.money_manager_app.databinding.TransactionItemBinding
import com.example.money_manager_app.utils.setOnSafeClickListener
import com.example.money_manager_app.utils.toFormattedTimeString

class TransactionAdapter(
    private val context: Context,
    private val currencySymbol: String,
    private val wallets: List<Wallet>,
    private val onTransactionClick: (Transaction) -> Unit = {}
) : ListAdapter<TransactionListItem, RecyclerView.ViewHolder>(TransactionListItemDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_DATE_HEADER = 0
        private const val VIEW_TYPE_TRANSACTION_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TransactionListItem.DateHeader -> VIEW_TYPE_DATE_HEADER
            is TransactionListItem.TransactionItem -> VIEW_TYPE_TRANSACTION_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_DATE_HEADER) {
            val binding =
                DateHeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DateHeaderViewHolder(binding)
        } else {
            val binding =
                TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            TransactionViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is TransactionListItem.DateHeader -> (holder as DateHeaderViewHolder).bind(item)
            is TransactionListItem.TransactionItem -> (holder as TransactionViewHolder).bind(
                item.transaction
            )
        }
    }

    inner class DateHeaderViewHolder(private val binding: DateHeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(header: TransactionListItem.DateHeader) {
            binding.apply {
                dayOfWeekLabel.text = header.dayOfWeek
                dayOfMonthLabel.text = header.dayOfMonth
                monthYearLabel.text = header.monthYear
                totalAmountLabel.text =
                    context.getString(R.string.money_amount, currencySymbol, header.total)
            }
        }
    }

    inner class TransactionViewHolder(private val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            when (transaction) {
                is GoalTransaction ->{
                    binding.transactionTypeTextView.text = context.getString(
                        R.string.goal_transaction_name,
                        transaction.type.toString(),
                        transaction.name
                    )
                    binding.transactionAmount.text = if(transaction.type == GoalInputType.DEPOSIT){
                        context.getString(R.string.negative_money_amount, currencySymbol, transaction.amount)
                    } else {
                        context.getString(R.string.positive_money_amount, currencySymbol, transaction.amount)
                    }
                }

                is DebtTransaction ->{
                    binding.transactionTypeTextView.text = context.getString(
                        R.string.debt_transaction_name,
                        transaction.action.toString(),
                        transaction.name
                    )
                    binding.transactionAmount.text = if(transaction.action == DebtActionType.DEBT_INCREASE){
                        context.getString(R.string.positive_money_amount, currencySymbol, transaction.amount)
                    } else {
                        context.getString(R.string.negative_money_amount, currencySymbol, transaction.amount)
                    }
                }

                else ->{
                    binding.transactionTypeTextView.text = transaction.name
                    binding.transactionAmount.text = context.getString(R.string.money_amount, currencySymbol, transaction.amount)
                }
            }
            binding.transactionTime.text = transaction.date.toFormattedTimeString()
            binding.walletName.text = wallets.find { it.id == transaction.walletId }?.name
            binding.root.setOnSafeClickListener {
                onTransactionClick(transaction)
            }
        }
    }
}

class TransactionListItemDiffCallback : DiffUtil.ItemCallback<TransactionListItem>() {
    override fun areItemsTheSame(
        oldItem: TransactionListItem,
        newItem: TransactionListItem
    ): Boolean {
        return if (oldItem is TransactionListItem.DateHeader && newItem is TransactionListItem.DateHeader) {
            oldItem.dayOfMonth == newItem.dayOfMonth && oldItem.monthYear == newItem.monthYear
        } else if (oldItem is TransactionListItem.TransactionItem && newItem is TransactionListItem.TransactionItem) {
            oldItem.transaction.id == newItem.transaction.id
        } else {
            false
        }
    }

    override fun areContentsTheSame(
        oldItem: TransactionListItem,
        newItem: TransactionListItem
    ): Boolean {
        return oldItem == newItem
    }
}