package com.monsieur.cloy.cryptostate.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.monsieur.cloy.cryptostate.ui.assets.PageAssets
import com.monsieur.cloy.cryptostate.ui.rates.PageRates

class ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        return when(position){
            0 -> PageRates.newInstance()
            1 -> PageAssets.newInstance()
            else -> PageRates.newInstance()
        }
    }
}