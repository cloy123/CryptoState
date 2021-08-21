package com.monsieur.cloy.cryptostate.ui.prices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.monsieur.cloy.cryptostate.databinding.FragmentPricesBinding
import com.monsieur.cloy.cryptostate.utilits.addHomeButton
import com.monsieur.cloy.cryptostate.utilits.replaceFragment
import com.monsieur.cloy.cryptostate.viewModels.MainViewModel

class PagePrices : Fragment() {

    private var _binding: FragmentPricesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerAdapter: PricesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
            Toast.makeText(context, recyclerAdapter.getSize().toString(), Toast.LENGTH_SHORT).show()
        })
    }

    private fun initFunc(){
        binding.add.setOnClickListener {
            replaceFragment(AddPrice())
            addHomeButton()
        }
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