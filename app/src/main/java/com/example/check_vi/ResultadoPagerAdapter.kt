package com.example.check_vi

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.check_vi.ResultadoFragment
import com.example.check_vi.RecomendacionFragment

class ResultadoPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ResultadoFragment()
            1 -> RecomendacionFragment()
            else -> ResultadoFragment()
        }
    }
}
