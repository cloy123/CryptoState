package com.monsieur.cloy.cryptostate.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.monsieur.cloy.cryptostate.R
import com.monsieur.cloy.cryptostate.databinding.FragmentMainBinding
import com.monsieur.cloy.cryptostate.utilits.deleteHomeButton

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        deleteHomeButton()
    }

    private fun createViewPager(){
        val pagerAdapter = activity?.let { ViewPagerAdapter(it) }
        binding.viewPager.adapter = pagerAdapter
        val tabLayoutMediator = TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = ""// getText(R.string.page_courses_name)
                1 -> tab.text = ""//getText(R.string.page_my_assets_state_name)
            }
        }
        tabLayoutMediator.attach()
    }
}