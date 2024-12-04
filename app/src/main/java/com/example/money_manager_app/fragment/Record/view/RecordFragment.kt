package com.example.money_manager_app.fragment.Record.view

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.data.model.Transaction
import com.example.money_manager_app.data.model.entity.DebtTransaction
import com.example.money_manager_app.data.model.entity.Transfer
import com.example.money_manager_app.data.model.entity.enums.DebtActionType
import com.example.money_manager_app.data.model.entity.enums.TransferType
import com.example.money_manager_app.databinding.FragmentRecordBinding
import com.example.money_manager_app.fragment.Record.viewmodel.RecordViewModel
import com.example.money_manager_app.utils.toFormattedDateString
import com.example.money_manager_app.utils.toFormattedTimeString
import com.example.money_manager_app.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.io.File

class RecordFragment  : BaseFragment<FragmentRecordBinding, RecordViewModel>(R.layout.fragment_record) {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun getVM(): RecordViewModel {
        val vm: RecordViewModel by activityViewModels()
        return vm
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        observeData()
        back()
        delete()
    }

    private fun delete() {
        binding.ivDelete.setOnClickListener {
        }
    }

    private fun back() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getVM().transaction.collect {
                    showHideLoading(it)
                }
            }
        }

    }

    private fun showHideLoading(transaction: Transaction){
       if(transaction.iconId != null){
           transaction.iconId?.let { binding.ivIcon.setImageResource(it) }
       }
        binding.amountLabel.text = transaction.amount.toString()
        binding.dateLabel.text = transaction.date.toFormattedDateString() + " " + transaction.time.toFormattedTimeString()
        val wallet = mainViewModel.currentAccount.value?.wallets
        val fromWallet = wallet?.find { it.id == transaction.walletId }
        when (transaction) {
            is Transfer -> {
                binding.categoryLabel.visibility = android.view.View.VISIBLE
                binding.categoryLabel.text = mainViewModel.categories.value?.find { it.id == transaction.categoryId }?.name
                binding.tvDescription.text = transaction.description
                if(transaction?.memo != null){
                    binding.memoTitleLabel.text = transaction.memo
                    binding.memoLabel.visibility = android.view.View.VISIBLE
                }
                if (transaction.typeOfExpenditure == TransferType.Transfer){
                    if(transaction?.fee != null){
                        binding.feeLabel.text = transaction.fee.toString()
                        binding.feeLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                        binding.feeTitleLabel.visibility = android.view.View.VISIBLE
                    }

                    val toWallet = wallet?.find { it.id == transaction.toWalletId }
                    binding.walletLabel.text = fromWallet?.name + " -> " + toWallet?.name
                    binding.typeLabel.text = transaction.typeOfExpenditure.name
                } else {
                    if(transaction.typeOfExpenditure == TransferType.Income){
                        binding.typeLabel.text = "Income"
                        binding.amountLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_2))
                    } else {
                        binding.typeLabel.text = "Expense"
                        binding.amountLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                    }
                    binding.walletLabel.text = fromWallet?.name
                }
                if(transaction.linkImg != null){
                    binding.ivImg.visibility = android.view.View.VISIBLE
                    val file = File(transaction.linkImg)
                    if (file.exists()) {
                        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                        binding.ivImg.setImageBitmap(bitmap)
                    }
                }
            }

            is DebtTransaction-> {
                if(transaction.action == DebtActionType.DEBT_INCREASE ){
                    binding.tvDescription.text = DebtActionType.DEBT_INCREASE.name
                    binding.typeLabel.text = "Income"
                    binding.amountLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_2))
                }
                if(transaction.action == DebtActionType.INTEREST ){
                    binding.tvDescription.text = DebtActionType.INTEREST.name
                }
                if(transaction.action == DebtActionType.REPAYMENT ){
                    binding.tvDescription.text = DebtActionType.REPAYMENT.name
                    binding.typeLabel.text = "Expense"
                    binding.amountLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

                }
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        val transaction = arguments?.getParcelable<Transaction>("transaction")
        if (transaction != null) {
            getVM().setTransaction(transaction)
        }
    }

}