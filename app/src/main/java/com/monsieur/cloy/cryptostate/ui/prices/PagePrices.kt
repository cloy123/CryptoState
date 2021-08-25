package com.monsieur.cloy.cryptostate.ui.prices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.monsieur.cloy.cryptostate.databinding.FragmentPricesBinding
import com.monsieur.cloy.cryptostate.utilits.replaceFragment
import com.monsieur.cloy.cryptostate.viewModels.MainViewModel

class PagePrices : Fragment() {

    private var _binding: FragmentPricesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerAdapter: PricesRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPricesBinding.inflate(inflater, container, false)
        initViewModel()
        initFunc()
        return binding.root
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.prices.observe(viewLifecycleOwner, Observer {
            recyclerAdapter.setItems(it)
        })
    }

    private fun initFunc(){
        initRecyclerAdapter()
    }

    private fun initRecyclerAdapter(){
        recyclerAdapter = PricesRecyclerAdapter()
        binding.recyclerView.adapter = recyclerAdapter
        if(viewModel.prices.value != null && !viewModel.prices.value!!.isEmpty()){
            recyclerAdapter.setItems(viewModel.prices.value!!)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PagePrices()
    }
}