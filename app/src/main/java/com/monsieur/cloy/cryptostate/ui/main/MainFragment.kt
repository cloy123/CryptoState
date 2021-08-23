package com.monsieur.cloy.cryptostate.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.monsieur.cloy.cryptostate.databinding.FragmentMainBinding
import com.monsieur.cloy.cryptostate.utilits.changeToolBar
import com.monsieur.cloy.cryptostate.viewModels.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        initViewModel()
        initFunc()
        return binding.root
    }

    private fun initViewModel(){
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    private fun initFunc(){
        createViewPager()
    }

    override fun onResume() {
        super.onResume()
        changeToolBar(menu = true, homeButton = false, "")
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