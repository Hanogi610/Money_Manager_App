package com.example.money_manager_app.fragment.detail.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.Transaction
import com.example.money_manager_app.data.model.entity.Debt
import com.example.money_manager_app.data.model.entity.DebtTransaction
import com.example.money_manager_app.data.model.entity.Transfer
import com.example.money_manager_app.data.model.entity.Wallet
import com.example.money_manager_app.data.model.entity.enums.DebtActionType
import com.example.money_manager_app.data.model.entity.enums.DebtType
import com.example.money_manager_app.data.model.entity.enums.TransferType
import com.example.money_manager_app.databinding.ItemTransferBinding
import com.example.money_manager_app.utils.toFormattedTimeString

class DetailDayAdapter(
    private var listTranfer : List<Transaction>,
    private var listWallet : List<Wallet>,
    private val onClick :(transaction: Transaction) -> Unit
) :RecyclerView.Adapter<DetailDayAdapter.DetailDayViewHolder>() {

    inner class DetailDayViewHolder(private var binding: ItemTransferBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            binding.tvTime.text = transaction.time.toFormattedTimeString()
            if (transaction is Transfer){
                when (transaction.typeOfExpenditure) {
                    TransferType.Expense -> {
                        binding.ivItem.setImageResource(transaction.iconId ?: R.drawable.expense_1)
                        binding.tvAmount.text = "-${transaction.amount}"
                        binding.tvAmount.setTextColor(
                            ContextCompat.getColor(binding.root.context, R.color.red)
                        )
                        binding.tvName.text = transaction.description
                        binding.tvBank.text = listWallet.find { it.id == transaction.walletId}?.name
                    }
                    TransferType.Income -> {
                        binding.ivItem.setImageResource(transaction.iconId ?: R.drawable.expense_1)
                        binding.tvAmount.text = "+${transaction.amount}"
                        binding.tvAmount.setTextColor(
                            ContextCompat.getColor(binding.root.context, R.color.blue)
                        )
                        binding.tvName.text = transaction.description
                        binding.tvBank.text = listWallet.find { it.id == transaction.walletId}?.name
                    }
                    else -> {
                        binding.ivItem.setImageResource(transaction.iconId ?: R.drawable.expense_1)
                        binding.tvAmount.text = "${transaction.amount}"
                        binding.tvAmount.setTextColor(
                            ContextCompat.getColor(binding.root.context, R.color.black)
                        )
                        binding.tvName.text = transaction.description
                        val bank = listWallet.find { it.id == transaction.walletId}?.name + " -> " + listWallet.find { it.id == transaction.toWalletId}?.name
                        binding.tvBank.text = bank
                    }
                }
            }
            if (transaction is Debt){
                when (transaction.type) {
                    DebtType.PAYABLE -> {
                        binding.tvAmount.text = "+${transaction.amount}"
                        binding.tvAmount.setTextColor(
                            ContextCompat.getColor(binding.root.context, R.color.blue)
                        )
                        binding.tvName.text = transaction.description
                        binding.tvBank.text = listWallet.find { it.id == transaction.walletId}?.name
                    }

                    DebtType.RECEIVABLE -> {
                        binding.tvAmount.text = "-${transaction.amount}"
                        binding.tvAmount.setTextColor(
                            ContextCompat.getColor(binding.root.context, R.color.red)
                        )
                        binding.tvName.text = transaction.description
                        binding.tvBank.text = listWallet.find { it.id == transaction.walletId}?.name
                    }
                }
            }

            if (transaction is DebtTransaction){
                when (transaction.action) {
                    DebtActionType.INTEREST -> {
                    }

                    DebtActionType.DEBT_INCREASE-> {
                        binding.tvAmount.text = "+${transaction.amount}"
                        binding.tvAmount.setTextColor(
                            ContextCompat.getColor(binding.root.context, R.color.blue)
                        )
                        binding.tvName.text = "Debt increase"
                        binding.tvBank.text = listWallet.find { it.id == transaction.walletId}?.name
                    }

                    DebtActionType.REPAYMENT -> {
                        binding.tvAmount.text = "-${transaction.amount}"
                        binding.tvAmount.setTextColor(
                            ContextCompat.getColor(binding.root.context, R.color.red)
                        )
                        binding.tvName.text = "Repayment"
                        binding.tvBank.text = listWallet.find { it.id == transaction.walletId}?.name
                    }
                }
            }
            binding.root.setOnClickListener {
                onClick(transaction)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailDayViewHolder {
        val binding = ItemTransferBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailDayViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listTranfer.size
    }
    override fun onBindViewHolder(holder: DetailDayViewHolder, position: Int) {
        holder.bind(listTranfer[position])
    }

    fun setWallets(wallets: List<Wallet>) {
        listWallet = wallets
        notifyDataSetChanged()
    }

    fun setTransfers(transfers: List<Transaction>) {
        listTranfer = transfers
        notifyDataSetChanged()
    }

}