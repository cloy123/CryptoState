package com.monsieur.cloy.cryptostate.ui.prices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.monsieur.cloy.cryptostate.databinding.FragmentPricesBinding
import com.monsieur.cloy.cryptostate.viewModels.MainViewModel

class PagePrices : Fragment() {

    private var _binding: FragmentPricesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private lateinit var recyclerAdapter: PricesRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPricesBinding.inflate(inflater, container, false)
        initFunc()
        return binding.root
    }

    private fun initFunc(){
        initRecyclerAdapter()
        viewModel.allPrices?.observe(viewLifecycleOwner, Observer {
            recyclerAdapter.setItems(it)
        })
    }

    private fun initRecyclerAdapter(){
        recyclerAdapter = PricesRecyclerAdapter()
        binding.recyclerView.adapter = recyclerAdapter
        if(viewModel.allPrices != null && viewModel.allPrices!!.value != null){
            recyclerAdapter.setItems(viewModel.allPrices!!.value!!)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PagePrices()
    }
}