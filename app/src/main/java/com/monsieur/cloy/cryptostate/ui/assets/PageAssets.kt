package com.monsieur.cloy.cryptostate.ui.assets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.monsieur.cloy.cryptostate.databinding.FragmentAssetsBinding
import com.monsieur.cloy.cryptostate.model.Assets.assetsInfo.AssetsInfo
import com.monsieur.cloy.cryptostate.viewModels.MainViewModel

class PageAssets : Fragment() {

    private var _binding: FragmentAssetsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerAdapter: AssetsRecyclerAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAssetsBinding.inflate(inflater, container, false)
        initFunc()
        return binding.root
    }

    private fun initFunc() {
        initRecyclerAdapter()
    }

    private fun initRecyclerAdapter(){
        recyclerAdapter = AssetsRecyclerAdapter()
        binding.recyclerView.adapter = recyclerAdapter
        if(viewModel.allAssets != null && viewModel.allAssets!!.value != null && viewModel.allAssets!!.value!!.isNotEmpty()){
            updateFields()
        }
        viewModel.assetsInfo.observe(viewLifecycleOwner, Observer {
            updateFields()
        })
        viewModel.allAssets?.observe(viewLifecycleOwner, Observer {
            if(it != null){
                recyclerAdapter.setItems(it)
            }
        })
    }


    private fun updateFields(){
        val assetsInfo: AssetsInfo = if(viewModel.assetsInfo.value.isNullOrEmpty()) {
            AssetsInfo()
        }else {
            val lastIndex = viewModel.assetsInfo.value?.size!!.minus(1)
            viewModel.assetsInfo.value?.get(lastIndex)!!
        }
        binding.usd.text = assetsInfo.quantityUSD.toString()
        binding.eur.text = assetsInfo.quantityEUR.toString()
        binding.rub.text = assetsInfo.quantityRUB.toString()
        binding.uah.text = assetsInfo.quantityUAH.toString()
        binding.usdChange.text = assetsInfo.changeUSD.toString()
        binding.eurChange.text = assetsInfo.changeEUR.toString()
        binding.rubChange.text = assetsInfo.changeRUB.toString()
        binding.uahChange.text = assetsInfo.changeUAH.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance() = PageAssets()
    }
}