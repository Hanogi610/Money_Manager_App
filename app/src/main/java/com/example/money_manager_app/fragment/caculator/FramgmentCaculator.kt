package com.example.money_manager_app.fragment.caculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragmentNotRequireViewModel
import com.example.money_manager_app.databinding.FragmentCaculatorBinding
import com.example.money_manager_app.fragment.add.viewmodel.AddViewModel
import net.objecthunter.exp4j.ExpressionBuilder

class FramgmentCaculator : BaseFragmentNotRequireViewModel<FragmentCaculatorBinding>(R.layout.fragment_caculator), View.OnClickListener {

    private val addViewModel: AddViewModel by activityViewModels()

    private var equation: String = "0"

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding.buttonClear.setOnClickListener(this)
        binding.button1.setOnClickListener(this)
        binding.button2.setOnClickListener(this)
        binding.button3.setOnClickListener(this)
        binding.button4.setOnClickListener(this)
        binding.button5.setOnClickListener(this)
        binding.button6.setOnClickListener(this)
        binding.button7.setOnClickListener(this)
        binding.button8.setOnClickListener(this)
        binding.button9.setOnClickListener(this)
        binding.button10.setOnClickListener(this)
        binding.button11.setOnClickListener(this)
        binding.button12.setOnClickListener(this)
        binding.button13.setOnClickListener(this)
        binding.button14.setOnClickListener(this)
        binding.button15.setOnClickListener(this)
        binding.button16.setOnClickListener(this)
        binding.button17.setOnClickListener(this)
        binding.button18.setOnClickListener(this)
        binding.backArrowButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.textView14.setOnClickListener {
            try {
                val expression = ExpressionBuilder(equation).build()
                val result = expression.evaluate()
                val longResult = result.toLong()
                if (result == longResult.toDouble()) {
                    binding.total.text = longResult.toString()
                    equation = longResult.toString()
                    addViewModel.setAmount(equation.toDouble())
                    findNavController().popBackStack()
                } else{
                    binding.total.text = result.toString()
                    equation = result.toString()
                    addViewModel.setAmount(equation.toDouble())
                    findNavController().popBackStack()
                }


            } catch (e: Exception) {
                Log.d("EXCEPTION", "Message: ${e.message}")
            }
        }
    }



    fun setTotal(total: String) {
        equation  = equation + total
        if(equation.length > 1){
            if(equation[0] == '0' && equation[1] != '.') {
                equation = equation.substring(1)
            }
        }
        binding.total.text = equation
    }

    private fun clear() {
        equation = "0"
        binding.total.text ="0"
    }

    override fun onClick(v: View?) {
        when(v?.getId()){
            R.id.button_16 -> {
                setTotal("0")
            }
            R.id.button_12 -> {
                setTotal("1")
            }
            R.id.button_13 -> {
                setTotal("2")
            }
            R.id.button_14 -> {
                setTotal("3")
            }
            R.id.button_8 -> {
                setTotal("4")
            }
            R.id.button_9 -> {
                setTotal("5")
            }
            R.id.button_10 -> {
                setTotal("6")
            }
            R.id.button_4 -> {
                setTotal("7")
            }
            R.id.button_5 -> {
                setTotal("8")
            }
            R.id.button_6 -> {
                setTotal("9")
            }
            R.id.button_17 -> {
                setTotal(".")
            }
            R.id.button_clear -> {
                clear()
            }
            R.id.button_15 -> {
                setTotal("+")
            }
            R.id.button_11 -> {
                setTotal("-")
            }
            R.id.button_7 -> {
                setTotal("*")
            }
            R.id.button_3 -> {
                setTotal("/")
            }
            R.id.button_1 -> {
                setTotal("+")
            }

            R.id.button_2 -> {
                setTotal("/")
            }
            R.id.button_18 -> {
                try {
                    val expression = ExpressionBuilder(equation).build()
                    val result = expression.evaluate()
                    val longResult = result.toLong()
                    if (result == longResult.toDouble()) {
                        binding.total.text = longResult.toString()
                        equation = longResult.toString()
                    } else
                        binding.total.text = result.toString()
                    equation = result.toString()

                } catch (e: Exception) {
                    Log.d("EXCEPTION", "Message: ${e.message}")
                }

            }
        }

    }


}