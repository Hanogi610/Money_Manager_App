package com.example.money_manager_app.fragment.add.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.Transaction
import com.example.money_manager_app.databinding.FragmentAddBinding
import com.example.money_manager_app.fragment.add.viewmodel.AddViewModel
import com.example.moneymanager.ui.add.adapter.AddPagerAdapter
import com.example.moneymanager.ui.add.adapter.AddTransferInterface
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment() {

    private val viewModel: AddViewModel by activityViewModels()
    private lateinit var binding: FragmentAddBinding
    private lateinit var adapter: AddPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        back()

    }

    fun setAdapter(){
        adapter = AddPagerAdapter(this)
        binding.vpAdd.adapter = adapter
        binding.vpAdd.setPageTransformer(DepthPageTransformer())
        binding.vpAdd.post(this::setTab)
        binding.vpAdd.post(this::tabLayout)
        binding.vpAdd.post(this::saveTransfer)
    }

    fun back(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackEvent()
            }
        })

        binding.ivBack.setOnClickListener {
            handleBackEvent()
        }
    }

    fun handleBackEvent(){
        viewModel.onCleared()
        findNavController().navigate(R.id.mainFragment)
    }

    fun setTab() {
        var position = arguments?.getInt("position") ?: 1
        if(arguments?.getParcelable<Transaction>("transition") != null){
            position = arguments?.getInt("key") ?: 1

        }
        binding.vpAdd.currentItem = position
        resetTabStyles()
        when (position) {
            0 -> setActiveTab(binding.tvIncome, R.drawable.customer_select_category_income)
            1 -> setActiveTab(binding.tvAddExpense, R.drawable.customer_select_category_expense)
            2 -> setActiveTab(binding.tvTransfer, R.drawable.custom_select_tranfer)
        }
        if(arguments?.getParcelable<Transaction>("transition") != null){
            val transaction = arguments?.getParcelable<Transaction>("transition")
            val currentFragment = childFragmentManager.findFragmentByTag("f" + binding.vpAdd.currentItem)
            if (currentFragment is AddTransferInterface) {
                if (transaction != null) {
                    viewModel.setDescriptor(transaction.name)
                    viewModel.setAmount(transaction.amount)
                }
            }
        }
        setCategory(position)
    }

    fun setCategory(position: Int) {
        val bundle = arguments
        val idCategory = bundle?.getInt("idCategory") ?: 0

        if (idCategory != 0) {
            when (position) {
                0 -> {

                    val listCategoryIncome = resources.getStringArray(R.array.category_income)
                    val pair : Pair<String, Int> = Pair(listCategoryIncome[idCategory - 1], idCategory)
                    viewModel.setCategoryNameIncome(pair)
                }
                1 -> {
                    val listCategoryExpense = resources.getStringArray(R.array.category_expense)
                    val pair : Pair<String, Int> = Pair(listCategoryExpense[idCategory - 1], idCategory)
                    viewModel.setCategoryNameExpense(pair)
                }
            }
        }
    }



    fun tabLayout() {
        val tabClickListener = View.OnClickListener { view ->
            resetTabStyles()
            when (view.id) {
                R.id.tv_income -> {
                    setActiveTab(binding.tvIncome, R.drawable.customer_select_category_income)
                    binding.vpAdd.currentItem = 0
                }
                R.id.tv_add_expense -> {
                    setActiveTab(binding.tvAddExpense, R.drawable.customer_select_category_expense)
                    binding.vpAdd.currentItem = 1
                }
                R.id.tv_transfer -> {
                    setActiveTab(binding.tvTransfer, R.drawable.custom_select_tranfer)
                    binding.vpAdd.currentItem = 2
                }
            }
        }

        binding.tvIncome.setOnClickListener(tabClickListener)
        binding.tvAddExpense.setOnClickListener(tabClickListener)
        binding.tvTransfer.setOnClickListener(tabClickListener)
    }

    private fun resetTabStyles() {
        binding.tvIncome.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.tvIncome.setBackgroundResource(0)
        binding.tvAddExpense.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.tvAddExpense.setBackgroundResource(0)
        binding.tvTransfer.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.tvTransfer.setBackgroundResource(0)
    }

    private fun setActiveTab(tab: TextView, backgroundRes: Int) {
        tab.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        tab.setBackgroundResource(backgroundRes)
    }

    fun saveTransfer() {
        binding.tvSave.setOnClickListener {
            val currentFragment = childFragmentManager.findFragmentByTag("f" + binding.vpAdd.currentItem)
            if (currentFragment is AddTransferInterface) {
                when (binding.vpAdd.currentItem) {
                    0 -> currentFragment.onSaveIncome()
                    1 -> currentFragment.onSaveExpense()
                    2 -> currentFragment.onSaveTransfer()
                }
            }
        }
    }
}

class DepthPageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        view.apply {
            when {
                position < -1 -> alpha = 0f
                position <= 0 -> {
                    alpha = 1f
                    translationX = 0f
                    translationZ = 0f
                    scaleX = 1f
                    scaleY = 1f
                }
                position <= 1 -> {
                    alpha = 1 - position
                    translationX = view.width * -position
                    translationZ = -1f
                    scaleX = 0.75f + (1 - 0.75f) * (1 - Math.abs(position))
                    scaleY = 0.75f + (1 - 0.75f) * (1 - Math.abs(position))
                }
                else -> alpha = 0f
            }
        }
    }
}