package com.example.money_manager_app.fragment.wallet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.money_manager_app.data.model.entity.Wallet
import com.example.money_manager_app.databinding.CustomeIconWalletBinding


class SelectWalletAdapter(private var listWallet: List<Wallet>, private val onItemClick: (Wallet) -> Unit) : RecyclerView.Adapter<SelectWalletAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CustomeIconWalletBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind (wallet: Wallet){
                binding.tvWallet.text = wallet.name.toString()
                var link = "wallet_" + wallet.iconId
                binding.ivItem.setImageResource(binding.root.context.resources.getIdentifier(link, "drawable", binding.root.context.packageName))
                binding.tvMoney.text = wallet.amount.toString()
                binding.root.setOnClickListener {
                    onItemClick(wallet)
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CustomeIconWalletBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listWallet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listWallet[position])
    }

    fun setUpdateDataWallet(wallets: List<Wallet>){
        listWallet = wallets
        notifyDataSetChanged()
    }
}