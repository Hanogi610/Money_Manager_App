package com.example.money_manager_app.fragment.wallet.add_debt_transaction

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.data.model.entity.Debt
import com.example.money_manager_app.data.model.entity.DebtTransaction
import com.example.money_manager_app.data.model.entity.enums.DebtActionType
import com.example.money_manager_app.databinding.FragmentAddDebtTransactionBinding
import com.example.money_manager_app.utils.setOnSafeClickListener
import com.example.money_manager_app.utils.toDateTimestamp
import com.example.money_manager_app.utils.toTimeTimestamp
import com.example.money_manager_app.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddDebtTransactionFragment :
    BaseFragment<FragmentAddDebtTransactionBinding, AddDebtTransactionViewModel>(R.layout.fragment_add_debt_transaction) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var debt: Debt? = null
    private var debTransaction: DebtTransaction? = null
    private val calendar = Calendar.getInstance()

    override fun getVM(): AddDebtTransactionViewModel {
        val vm: AddDebtTransactionViewModel by viewModels()
        return vm
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        arguments?.let {
            debt = it.getParcelable("debt")
            debTransaction = it.getParcelable("debtTransaction")
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        val debtAction = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item,
            DebtActionType.entries.map { it.name })
        binding.spinnerActionType.adapter = debtAction
        val walletAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item,
            mainViewModel.currentAccount.value!!.wallets.map { it.name })
        binding.spinnerWallet.adapter = walletAdapter
        binding.spinnerWallet.setSelection(0)

        // Pre-fill fields if editing
        debTransaction?.let { transaction ->
            binding.etName.setText(transaction.name)
            binding.etAmount.setText(getString(R.string.money_amount, "", transaction.amount))
            binding.etDate.text =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(transaction.date)
            binding.etTime.text =
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(transaction.time)

            val actionTypeIndex = DebtActionType.entries.indexOf(transaction.action)
            binding.spinnerActionType.setSelection(actionTypeIndex)
            val walletName =
                mainViewModel.currentAccount.value!!.wallets.find { it.id == transaction.fromWallet }!!.name
            binding.spinnerWallet.setSelection(walletAdapter.getPosition(walletName))
            binding.delete.visibility = View.VISIBLE
        }

        // Default setup for date and time fields
        if (debTransaction == null) {
            binding.etDate.text =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
            binding.etTime.text =
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)
        }

        binding.etName.addTextChangedListener(textWatcher)
        binding.etAmount.addTextChangedListener(textWatcher)
        binding.etDate.addTextChangedListener(textWatcher)
        binding.etTime.addTextChangedListener(textWatcher)
    }

    override fun initToolbar() {
        super.initToolbar()

        binding.backButton.setOnClickListener {
            appNavigation.navigateUp()
        }

        binding.saveButton.setOnClickListener {
            val name = binding.etName.text.toString()
            val amount = binding.etAmount.text.toString().toDouble()
            val date = binding.etDate.text.toString().toDateTimestamp()
            val time = binding.etTime.text.toString().toTimeTimestamp()
            val wallet = mainViewModel.accounts.value.flatMap { it.wallets }
                .find { it.name == binding.spinnerWallet.selectedItem.toString() }!!.id
            val actionType =
                DebtActionType.valueOf(binding.spinnerActionType.selectedItem.toString())

            // Create or update transaction
            val debtTransaction = debTransaction?.copy(
                name = name,
                amount = amount,
                date = date,
                time = time,
                fromWallet = wallet,
                toWallet = debt!!.fromWallet,
                action = actionType
            ) ?: DebtTransaction(
                name = name,
                amount = amount,
                date = date,
                time = time,
                fromWallet = wallet,
                toWallet = debt!!.fromWallet,
                action = actionType,
                accountId = mainViewModel.currentAccount.value!!.account.id,
                debtId = debt!!.id
            )

            Log.d("hoangph", "debtTransaction: $debtTransaction")

            if (debTransaction == null) {
                getVM().addDebtTransaction(debtTransaction)
            } else {
                getVM().updateDebtTransaction(debtTransaction)
            }

            appNavigation.navigateUp()
        }
    }


    override fun setOnClick() {
        super.setOnClick()

        binding.etDate.setOnSafeClickListener {
            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    binding.etDate.text = SimpleDateFormat(
                        "dd/MM/yyyy", Locale.getDefault()
                    ).format(binding.etDate.text ?: selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        binding.etTime.setOnSafeClickListener {
            val timePicker = TimePickerDialog(
                requireContext(), { _, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    binding.etTime.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                        binding.etTime.text ?: selectedTime.time
                    )
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
            )
            timePicker.show()
        }

        binding.delete.setOnSafeClickListener {
            debTransaction?.let {
                getVM().deleteDebtTransaction(it.id)
            }
            appNavigation.navigateUp()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            checkSaveButton()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun checkSaveButton() {
        val name = binding.etName.text.toString()
        val amount = binding.etAmount.text.toString().toDoubleOrNull()
        val date = binding.etDate.text.toString()
        val time = binding.etTime.text.toString()
        binding.saveButton.isEnabled =
            amount != null && name.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()
    }
}