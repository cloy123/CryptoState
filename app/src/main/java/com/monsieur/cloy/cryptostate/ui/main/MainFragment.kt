package com.monsieur.cloy.cryptostate.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.monsieur.cloy.cryptostate.databinding.FragmentMainBinding
import com.monsieur.cloy.cryptostate.utilits.changeToolBar
import com.monsieur.cloy.cryptostate.utilits.currentTabPosition

class MainFragment : Fragment(), TabLayout.OnTabSelectedListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        initFunc()
        return binding.root
    }

    private fun initFunc(){
        createViewPager()
    }

    override fun onResume() {
        super.onResume()
        changeToolBar(menu = true, homeButton = false, "")
        binding.tabs.selectTab(binding.tabs.getTabAt(currentTabPosition))
    }

    private fun createViewPager(){
        val pagerAdapter = activity?.let { ViewPagerAdapter(it) }
        binding.viewPager.adapter = pagerAdapter
        val tabLayoutMediator = TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = ""
                1 -> tab.text = ""
            }
        }
        tabLayoutMediator.attach()
        binding.tabs.addOnTabSelectedListener(this)
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        currentTabPosition = binding.tabs.selectedTabPosition
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}
}