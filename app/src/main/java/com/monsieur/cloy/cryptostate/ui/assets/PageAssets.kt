package com.monsieur.cloy.cryptostate.ui.assets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.monsieur.cloy.cryptostate.databinding.FragmentAssetsBinding
import com.monsieur.cloy.cryptostate.utilits.*
import com.monsieur.cloy.cryptostate.viewModels.MainViewModel

class PageAssets : Fragment() {

    private var _binding: FragmentAssetsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerAdapter: AssetsRecyclerAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAssetsBinding.inflate(inflater, container, false)
        initViewModel()
        initFunc()
        return binding.root
    }

    private fun initFunc() {
        initRecyclerAdapter()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    private fun initRecyclerAdapter(){
        recyclerAdapter = AssetsRecyclerAdapter()
        binding.recyclerView.adapter = recyclerAdapter
        if(viewModel.assets.value != null && !viewModel.assets.value!!.isEmpty()){
            updateFields()
        }
        viewModel.assets.observe(viewLifecycleOwner, Observer {
            updateFields()
        })
    }

    private fun updateFields(){
        val assets = viewModel.assets.value!!
        recyclerAdapter.setItems(assets)
        if(!assets.isEmpty()){
            binding.usd.text = assets.quantityUSD.toString()
            binding.eur.text = assets.quantityEUR.toString()
            binding.rub.text = assets.quantityRUB.toString()
            binding.uah.text = assets.quantityUAH.toString()
            binding.usdChange.text = assets.changeUSD.toString()
            binding.eurChange.text = assets.changeEUR.toString()
            binding.rubChange.text = assets.changeRUB.toString()
            binding.uahChange.text = assets.changeUAH.toString()
        }else
        {
            binding.usd.text = "0"
            binding.eur.text = "0"
            binding.rub.text = "0"
            binding.uah.text = "0"
            binding.usdChange.text = "0"
            binding.eurChange.text = "0"
            binding.rubChange.text = "0"
            binding.uahChange.text = "0"
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PageAssets()
    }
}