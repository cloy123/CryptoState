package com.monsieur.cloy.cryptostate.ui.assets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monsieur.cloy.cryptostate.databinding.FragmentAssetsBinding
import com.monsieur.cloy.cryptostate.model.Assets.Asset
import com.monsieur.cloy.cryptostate.model.Assets.Assets
import com.monsieur.cloy.cryptostate.utilits.Categories
import com.monsieur.cloy.cryptostate.utilits.Currency

class PageAssets : Fragment() {

    private var _binding: FragmentAssetsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerAdapter: AssetsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAssetsBinding.inflate(inflater, container, false)
        initViewModel()
        initFunc()
        return binding.root
    }

    val items = Assets()
    private fun initFunc() {
        initRecyclerAdapter()
        binding.add.setOnClickListener {
            items.items.add(Asset("wqe", "symbol", Categories.Crypto, Currency.USD))
            recyclerAdapter.setItems(items)
        }
    }

    private fun initViewModel() {
//        TODO("Not yet implemented")
    }

    private fun initRecyclerAdapter(){
        recyclerAdapter = AssetsRecyclerAdapter()
        binding.recyclerView.adapter = recyclerAdapter
//        if(viewModel.prices.value != null && !viewModel.prices.value!!.isEmpty()){
//            recyclerAdapter.setItems(viewModel.prices.value!!)
//        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PageAssets()
    }
}