package com.example.money_manager_app.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.money_manager_app.R
import com.example.money_manager_app.base.fragment.BaseFragment
import com.example.money_manager_app.databinding.FragmentInnitAccountBinding

class InnitAccountFragment : BaseFragment<FragmentInnitAccountBinding,InnitAccountViewModel>(R.layout.fragment_innit_account) {

    override fun getVM(): InnitAccountViewModel {
        val viewModel: InnitAccountViewModel by viewModels()
        return viewModel
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }
}