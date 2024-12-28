package com.example.money_manager_app.fragment.detail.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.Transaction
import com.example.money_manager_app.data.model.WalletItem
import com.example.money_manager_app.data.model.entity.Debt
import com.example.money_manager_app.data.model.entity.DebtTransaction
import com.example.money_manager_app.data.model.entity.Transfer
import com.example.money_manager_app.data.model.entity.enums.DebtActionType
import com.example.money_manager_app.data.model.entity.enums.DebtType
import com.example.money_manager_app.data.model.entity.enums.TransferType
import com.example.money_manager_app.databinding.ItemTransferBinding
import com.example.money_manager_app.utils.toFormattedTimeString

class DetailDayAdapter(
    private var listTranfer: List<Transaction>,
    private var listWallet: List<WalletItem>,
    private val onClick: (transaction: Transaction) -> Unit
) : RecyclerView.Adapter<DetailDayAdapter.DetailDayViewHolder>() {

    inner class DetailDayViewHolder(private var binding: ItemTransferBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            binding.tvTime.text = transaction.time.toFormattedTimeString()
            if (transaction is Transfer) {
                when (transaction.typeOfExpenditure) {
                    TransferType.Expense -> {
                        binding.ivItem.setImageResource(transaction.iconId ?: R.drawable.expense_1)
                        binding.tvAmount.text = "-${transaction.amount}"
                        binding.tvAmount.setTextColor(
                            ContextCompat.getColor(binding.root.context, R.color.red)
                        )
                        binding.tvName.text = transaction.description
                        binding.tvBank.text =
                            listWallet.find { it.wallet.id == transaction.walletId }?.wallet?.name
                                ?: ""
                    }

                    TransferType.Income -> {
                        binding.ivItem.setImageResource(transaction.iconId ?: R.drawable.expense_1)
                        binding.tvAmount.text = "+${transaction.amount}"
                        binding.tvAmount.setTextColor(
                            ContextCompat.getColor(binding.root.context, R.color.blue)
                        )
                        binding.tvName.text = transaction.description
                        binding.tvBank.text =
                            listWallet.find { it.wallet.id == transaction.walletId }?.wallet?.name
                                ?: ""
                    }

                    else -> {
                        binding.ivItem.setImageResource(transaction.iconId ?: R.drawable.expense_1)
                        binding.tvAmount.text = "${transaction.amount}"
                        binding.tvAmount.setTextColor(
                            ContextCompat.getColor(binding.root.context, R.color.black)
                        )
                        binding.tvName.text = transaction.description
                        val bank =
                            listWallet.find { it.wallet.id == transaction.walletId }?.wallet?.name + " -> " + listWallet.find { it.wallet.id == transaction.walletId }?.wallet?.name
                        binding.tvBank.text = bank
                    }
                }
            }
            if (transaction is Debt) {
                when (transaction.type) {
                    DebtType.PAYABLE -> {
                        binding.tvAmount.text = "+${transaction.amount}"
                        binding.tvAmount.setTextColor(
                            ContextCompat.getColor(binding.root.context, R.color.blue)
                        )
                        binding.tvName.text = transaction.description
                        binding.tvBank.text =
                            listWallet.find { it.wallet.id == transaction.walletId}?.wallet?.name ?: ""
                    }

                    DebtType.RECEIVABLE -> {
                        binding.tvAmount.text = "-${transaction.amount}"
                        binding.tvAmount.setTextColor(
                            ContextCompat.getColor(binding.root.context, R.color.red)
                        )
                        binding.tvName.text = transaction.description
                        binding.tvBank.text =
                            listWallet.find { it.wallet.id == transaction.walletId}?.wallet?.name ?: ""
                    }
                }
            }

            if (transaction is DebtTransaction) {
                when (transaction.action) {
                    DebtActionType.LOAN_INTEREST -> {
                    }

                    DebtActionType.DEBT_INCREASE -> {
                        binding.tvAmount.text = "+${transaction.amount}"
                        binding.tvAmount.setTextColor(
                            ContextCompat.getColor(binding.root.context, R.color.blue)
                        )
                        binding.tvName.text = "Debt increase"
                        binding.tvBank.text =
                            listWallet.find { it.wallet.id == transaction.walletId}?.wallet?.name ?: ""
                    }

                    DebtActionType.REPAYMENT -> {
                        binding.tvAmount.text = "-${transaction.amount}"
                        binding.tvAmount.setTextColor(
                            ContextCompat.getColor(binding.root.context, R.color.red)
                        )
                        binding.tvName.text = "Repayment"
                        binding.tvBank.text =
                            listWallet.find { it.wallet.id == transaction.walletId}?.wallet?.name ?: ""
                    }

                    DebtActionType.DEBT_COLLECTION -> {
                        binding.tvAmount.text = "+${transaction.amount}"
                        binding.tvAmount.setTextColor(
                            ContextCompat.getColor(binding.root.context, R.color.blue)
                        )
                        binding.tvName.text = "Debt collection"
                        binding.tvBank.text =
                            listWallet.find { it.wallet.id == transaction.walletId}?.wallet?.name ?: ""
                    }

                    DebtActionType.LOAN_INCREASE -> {
                        binding.tvAmount.text = "-${transaction.amount}"
                        binding.tvAmount.setTextColor(
                            ContextCompat.getColor(binding.root.context, R.color.blue)
                        )
                        binding.tvName.text = "Loan increase"
                        binding.tvBank.text =
                            listWallet.find { it.wallet.id == transaction.walletId}?.wallet?.name ?: ""
                    }

                    DebtActionType.DEBT_INTEREST -> {}
                }
            }
            binding.root.setOnClickListener {
                onClick(transaction)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailDayViewHolder {
        val binding =
            ItemTransferBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailDayViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listTranfer.size
    }

    override fun onBindViewHolder(holder: DetailDayViewHolder, position: Int) {
        holder.bind(listTranfer[position])
    }

    fun setWallets(wallets: List<WalletItem>) {
        listWallet = wallets
        notifyDataSetChanged()
    }

    fun setTransfers(transfers: List<Transaction>) {
        listTranfer = transfers
        notifyDataSetChanged()
    }

}