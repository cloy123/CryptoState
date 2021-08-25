package com.monsieur.cloy.cryptostate.ui.assets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.monsieur.cloy.cryptostate.R
import com.monsieur.cloy.cryptostate.databinding.FragmentAddAssetBinding
import com.monsieur.cloy.cryptostate.utilits.backButton
import com.monsieur.cloy.cryptostate.utilits.changeToolBar
import com.monsieur.cloy.cryptostate.utilits.showToast
import com.monsieur.cloy.cryptostate.viewModels.MainViewModel
import java.util.ArrayList

class AddAsset : Fragment() {

    private lateinit var _binding: FragmentAddAssetBinding
    private val binding get() = _binding
    private val pricesArray = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddAssetBinding.inflate(inflater, container, false)
        getPricesArray()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        changeToolBar(menu = false, homeButton = true, getString(R.string.add_asset_title))
        val arrayAdapter = context?.let { ArrayAdapter<String>(it, R.layout.support_simple_spinner_dropdown_item, pricesArray) }
        binding.spinnerCurrency.adapter = arrayAdapter
        binding.spinnerCurrency.setSelection(0)

        binding.saveButton.setOnClickListener {
            if(binding.assetName.text.trim().isEmpty()){
                showToast(getString(R.string.fields_not_filled))
            }
            val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
            viewModel.saveAsset(binding.assetName.text.toString(), pricesArray[binding.spinnerCurrency.selectedItemPosition])
            backButton()
        }
        binding.cancelButton.setOnClickListener {
            backButton()
        }
    }

    private fun getPricesArray(){
        val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val prices = viewModel.prices.value
        if (prices != null) {
            for (price in prices.items){
                pricesArray.add(price.symbolName)
            }
        }
    }
}