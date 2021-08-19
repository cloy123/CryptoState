package com.monsieur.cloy.cryptostate.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.monsieur.cloy.cryptostate.databinding.FragmentMainBinding
import com.monsieur.cloy.cryptostate.utilits.deleteHomeButton
import com.monsieur.cloy.cryptostate.viewModels.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
//        binding.spinner.setOnItemClickListener { _, _, position, _ ->
//            val array :List<Price.Companion.Categories> = when(position){
//                0 -> List(5){Price.Companion.Categories.Fiat; Price.Companion.Categories.Crypto; Price.Companion.Categories.Stock; Price.Companion.Categories.Bonds; Price.Companion.Categories.Other}
//                1 -> List(1){Price.Companion.Categories.Fiat}
//                2 -> List(1){Price.Companion.Categories.Crypto}
//                3 -> List(1){Price.Companion.Categories.Stock}
//                4 -> List(1){Price.Companion.Categories.Bonds}
//                5 -> List(2){Price.Companion.Categories.Stock; Price.Companion.Categories.Bonds}
//                6 -> List(1){Price.Companion.Categories.Other}
//                else -> List(5){Price.Companion.Categories.Fiat; Price.Companion.Categories.Crypto; Price.Companion.Categories.Stock; Price.Companion.Categories.Bonds; Price.Companion.Categories.Other}
//            }
//            mainViewModel.categories.value = array
//        }
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