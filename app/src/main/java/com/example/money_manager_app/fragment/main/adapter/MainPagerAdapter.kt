package com.example.moneymanager.ui.main_screen.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.money_manager_app.fragment.calendar.CalendarFragment
import com.example.money_manager_app.fragment.home.HomeFragment
import com.example.money_manager_app.fragment.statistic.StatisticFragment
import com.example.money_manager_app.fragment.wallet.WalletFragment

class MainPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4  // Number of tabs

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> CalendarFragment()
            2 -> StatisticFragment()
            3 -> WalletFragment()
            else -> HomeFragment()
        }
    }
}
