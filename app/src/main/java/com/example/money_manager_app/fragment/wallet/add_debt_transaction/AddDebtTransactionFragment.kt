package com.example.money_manager_app.fragment.wallet.add_debt_transaction

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.data.model.entity.DebtTransaction
import com.example.money_manager_app.data.model.entity.enums.DebtActionType
import com.example.money_manager_app.databinding.FragmentAddDebtTransactionBinding
import com.example.money_manager_app.fragment.wallet.WalletViewModel
import com.example.money_manager_app.utils.toDateTimestamp
import com.example.money_manager_app.utils.toTimeTimestamp
import com.example.moneymanager.ui.wallet_screen.add_debt_transaction.AddDebtTransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddDebtTransactionFragment :
    BaseFragment<FragmentAddDebtTransactionBinding, AddDebtTransactionViewModel>(R.layout.fragment_add_debt_transaction) {

    override fun getVM(): AddDebtTransactionViewModel {
        val vm: AddDebtTransactionViewModel by viewModels()
        return vm
    }


//
//        binding.backButton.setOnClickListener {
//            findNavController().popBackStack()
//        }
//
//        val debtAction = ArrayAdapter(requireContext(),
//            android.R.layout.simple_spinner_item,
//            DebtActionType.entries.map { it.name })
//        binding.spinnerActionType.adapter = debtAction
//        binding.spinnerActionType.setSelection(0) // Set default selection as first item
//
//        // Set initial date and time to current date and time
//        val calendar = Calendar.getInstance()
//        binding.etDate.text =
//            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
//        binding.etTime.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)
//
//        binding.etDate.setOnClickListener {
//            val datePicker = DatePickerDialog(
//                requireContext(),
//                { _, year, month, dayOfMonth ->
//                    val selectedDate = Calendar.getInstance()
//                    selectedDate.set(year, month, dayOfMonth)
//                    binding.etDate.text = SimpleDateFormat(
//                        "dd/MM/yyyy",
//                        Locale.getDefault()
//                    ).format(selectedDate.time)
//                },
//                calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.DAY_OF_MONTH)
//            )
//            datePicker.show()
//        }
//
//        binding.etTime.setOnClickListener {
//            val timePicker = TimePickerDialog(
//                requireContext(), { _, hourOfDay, minute ->
//                    val selectedTime = Calendar.getInstance()
//                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
//                    selectedTime.set(Calendar.MINUTE, minute)
//                    binding.etTime.text =
//                        SimpleDateFormat("HH:mm", Locale.getDefault()).format(selectedTime.time)
//                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
//            )
//            timePicker.show()
//        }
//
//        lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                mainViewModel.accounts.collect { accounts ->
//                    val wallets = accounts.flatMap { it.wallets }
//                    val walletNames = wallets.map { it.name }
//                    val walletAdapter = ArrayAdapter(
//                        requireContext(), android.R.layout.simple_spinner_item, walletNames
//                    )
//                    binding.spinnerWallet.adapter = walletAdapter
//                    binding.spinnerWallet.setSelection(0) // Set default selection as first item
//                }
//            }
//        }
//
//        // Add TextWatchers to EditTexts
//        binding.etName.addTextChangedListener(textWatcher)
//        binding.etAmount.addTextChangedListener(textWatcher)
//        binding.etDate.addTextChangedListener(textWatcher)
//        binding.etTime.addTextChangedListener(textWatcher)
//
//        binding.saveButton.setOnClickListener {
//            val name = binding.etName.text.toString()
//            val amount = binding.etAmount.text.toString().toDouble()
//            val date = binding.etDate.text.toString().toDateTimestamp()
//            val time = binding.etTime.text.toString().toTimeTimestamp()
//            val wallet = mainViewModel.accounts.value.flatMap { it.wallets }
//                .find { it.name == binding.spinnerWallet.selectedItem.toString() }!!.id
//            val actionType =
//                DebtActionType.valueOf(binding.spinnerActionType.selectedItem.toString())
//            val debtTransaction = DebtTransaction(
//                name = name,
//                amount = amount,
//                date = date,
//                time = time,
//                walletId = wallet,
//                action = actionType,
//                accountId = mainViewModel.currentAccount.value!!.account.id,
//                debtId = mainViewModel.currentDebt.value!!.id
//            )
//            viewModel.addDebtTransaction(debtTransaction)
//            findNavController().popBackStack()
//        }
//    }
//
//    private val textWatcher = object : TextWatcher {
//        override fun afterTextChanged(s: Editable?) {
//            checkSaveButton()
//        }
//
//        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//    }
//
//    private fun checkSaveButton() {
//        val name = binding.etName.text.toString()
//        val amount = binding.etAmount.text.toString().toDoubleOrNull()
//        val date = binding.etDate.text.toString()
//        val time = binding.etTime.text.toString()
//        binding.saveButton.isEnabled =
//            amount != null && name.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()
//    }
}