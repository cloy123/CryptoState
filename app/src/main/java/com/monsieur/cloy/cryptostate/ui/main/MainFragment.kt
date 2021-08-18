package com.monsieur.cloy.cryptostate.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.monsieur.cloy.cryptostate.R
import com.monsieur.cloy.cryptostate.databinding.FragmentMainBinding
import com.monsieur.cloy.cryptostate.model.Rates.Rate
import com.monsieur.cloy.cryptostate.utilits.APP_ACTIVITY
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
//            val array :List<Rate.Companion.Categories> = when(position){
//                0 -> List(5){Rate.Companion.Categories.Fiat; Rate.Companion.Categories.Crypto; Rate.Companion.Categories.Stock; Rate.Companion.Categories.Bonds; Rate.Companion.Categories.Other}
//                1 -> List(1){Rate.Companion.Categories.Fiat}
//                2 -> List(1){Rate.Companion.Categories.Crypto}
//                3 -> List(1){Rate.Companion.Categories.Stock}
//                4 -> List(1){Rate.Companion.Categories.Bonds}
//                5 -> List(2){Rate.Companion.Categories.Stock; Rate.Companion.Categories.Bonds}
//                6 -> List(1){Rate.Companion.Categories.Other}
//                else -> List(5){Rate.Companion.Categories.Fiat; Rate.Companion.Categories.Crypto; Rate.Companion.Categories.Stock; Rate.Companion.Categories.Bonds; Rate.Companion.Categories.Other}
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