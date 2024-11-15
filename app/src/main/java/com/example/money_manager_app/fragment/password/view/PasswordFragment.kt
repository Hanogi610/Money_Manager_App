package com.example.money_manager_app.fragment.password.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.viewModels
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.databinding.FragmentPasswordBinding
import com.example.money_manager_app.fragment.password.viewmodel.PasswordType
import com.example.money_manager_app.fragment.password.viewmodel.PasswordViewmodel
import com.example.money_manager_app.utils.setOnSafeClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordFragment : BaseFragment<FragmentPasswordBinding, PasswordViewmodel>(R.layout.fragment_password) {

    private val numbersInput = mutableListOf(
        binding.keyboard.number1,
        binding.keyboard.number2,
        binding.keyboard.number3,
        binding.keyboard.number4,
        binding.keyboard.number5,
        binding.keyboard.number6,
        binding.keyboard.number7,
        binding.keyboard.number8,
        binding.keyboard.number9,
    )

    private val numberDisplay = mutableListOf(
        binding.pc1,
        binding.pc2,
        binding.pc3,
        binding.pc3,
        binding.pc4,
        binding.pc5,
    )

    override fun getVM(): PasswordViewmodel {
        val viewModel: PasswordViewmodel by viewModels()
        return viewModel
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        numbersInput.forEachIndexed { _, numberInput ->
            numberInput.setOnSafeClickListener {
                getVM().addNumber(numberInput.text.toString())
            }
        }
    }

    override fun setOnClick() {
        super.setOnClick()

        binding.keyboard.backspace.setOnSafeClickListener {
            getVM().deleteNumber()
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()

        getVM().numbersEnter.observe(viewLifecycleOwner) {
            it.forEach { (index, number) ->
                if (number.isEmpty()) {
                    numberDisplay[index].setText(R.string.subtract)
                } else {
                    numberDisplay[index].setText(number)
                }
            }
        }

        getVM().currentCursor.observe(viewLifecycleOwner) {
            numberDisplay.forEachIndexed { index, numberInput ->
                if (index == it) {
                    if (numberInput.text.isNotEmpty()) {
                        numberInput.setText("")
                    }
                    numberInput.requestFocus()
                }
            }
        }

        getVM().currentPasswordType.observe(viewLifecycleOwner) {
            when (it) {
                PasswordType.CREATE -> {
                    binding.tvInput.setText(R.string.enter_your_pass_code)
                }
                PasswordType.CONFIRM -> {
                    binding.tvInput.setText(R.string.confirm_your_pass_code)
                }
                PasswordType.CHECK -> {
                    binding.tvInput.setText(R.string.confirm_your_pass_code)
                }
                else -> {
                    binding.tvInput.setText(R.string.confirm_your_pass_code)
                }
            }
        }

        getVM().isPasswordCorrect.observe(viewLifecycleOwner) {
            if (it) {
                appNavigation.openPasswordToCreateAccountScreen()
            } else {
                binding.groupIncoming.isActivated = true

                val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))

                Handler(Looper.getMainLooper()).postDelayed({
                    getVM().reset()
                }, 1000)
            }
        }
    }

}