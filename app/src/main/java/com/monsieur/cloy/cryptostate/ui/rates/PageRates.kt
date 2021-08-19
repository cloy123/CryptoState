package com.monsieur.cloy.cryptostate.ui.rates

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.monsieur.cloy.cryptostate.databinding.FragmentRatesBinding
import com.monsieur.cloy.cryptostate.utilits.addHomeButton
import com.monsieur.cloy.cryptostate.utilits.replaceFragment
import com.monsieur.cloy.cryptostate.viewModels.RatesViewModel

class PageRates : Fragment() {

    private var _binding: FragmentRatesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RatesViewModel
    private lateinit var recyclerAdapter: RatesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRatesBinding.inflate(inflater, container, false)
        initViewModel()
        initFunc()
        return binding.root
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(requireActivity()).get(RatesViewModel::class.java)
        viewModel.rates.observe(this, Observer {
            recyclerAdapter.setItems(it)
            Toast.makeText(context, recyclerAdapter.getSize().toString(), Toast.LENGTH_SHORT).show()
        })
    }

    private fun initFunc(){
        binding.add.setOnClickListener {
            replaceFragment(AddRate())
            addHomeButton()
        }
        binding.refresh.setOnClickListener { viewModel.updateRates() }
        initRecyclerAdapter()
    }

    private fun initRecyclerAdapter(){
        recyclerAdapter = RatesRecyclerAdapter()
        binding.recyclerView.adapter = recyclerAdapter
        if(viewModel.rates.value != null && !viewModel.rates.value!!.isEmpty()){
            recyclerAdapter.setItems(viewModel.rates.value!!)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PageRates()
    }
}