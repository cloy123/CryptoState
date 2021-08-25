package com.monsieur.cloy.cryptostate.ui.assets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.monsieur.cloy.cryptostate.R
import com.monsieur.cloy.cryptostate.databinding.FragmentEditAssetBinding
import com.monsieur.cloy.cryptostate.model.Assets.Asset
import com.monsieur.cloy.cryptostate.model.Prices.Price
import com.monsieur.cloy.cryptostate.utilits.backButton
import com.monsieur.cloy.cryptostate.utilits.changeToolBar
import com.monsieur.cloy.cryptostate.utilits.showToast
import com.monsieur.cloy.cryptostate.viewModels.MainViewModel

class EditAsset(private var asset: Asset, private var price: Price) : Fragment() {

    private lateinit var _binding: FragmentEditAssetBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditAssetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        updateFields()
        changeToolBar(menu = false, homeButton = true, getString(R.string.edit_asset_title))
        binding.cancelButton.setOnClickListener {
            backButton()
        }

        binding.buy.setOnClickListener {
            if(binding.buyPrice.text.trim().isNotEmpty() && binding.buyQuantity.text.trim().isNotEmpty()){
                asset.buy(binding.buyQuantity.text.toString().toFloat(), binding.buyPrice.text.toString().toFloat(), price)
                updateFields()
            }
            else{
                showToast(getString(R.string.fields_not_filled))
            }
        }

        binding.sell.setOnClickListener {
            if(binding.sellPrice.text.trim().isNotEmpty() || binding.sellQuantity.text.trim().isNotEmpty()){
                asset.sell(binding.sellQuantity.text.toString().toFloat(), binding.sellPrice.text.toString().toFloat(), price)
                updateFields()
            }
            else{
                showToast(getString(R.string.fields_not_filled))
            }
        }

        binding.saveButton.setOnClickListener {
            if(binding.assetName.text.trim().isEmpty()){
                showToast(getString(R.string.fields_not_filled))
                return@setOnClickListener
            }else{
                val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
                asset.asset = binding.assetName.text.trim().toString()
                viewModel.saveAsset(asset)
                backButton()
            }
        }
    }

    private fun updateFields(){
        binding.assetName.setText(asset.asset)
        binding.currency.text = asset.symbol
        binding.quantity.text = asset.mainQuantity.toString()
        binding.quantityUSD.text = asset.quantityUSD.toString()
        binding.averagePrice.text = asset.averagePrice.toString()
        if(asset.isDefaultFiatAsset){
            binding.averagePrice.text = ""
            binding.tvBuyPrice.visibility = View.GONE
            binding.tvSellPrice.visibility = View.GONE
            binding.buyPrice.visibility = View.GONE
            binding.sellPrice.visibility = View.GONE
            binding.linearAveragePrice.visibility = View.GONE
            binding.buyPrice.setText("1")
            binding.sellPrice.setText("1")
        }
    }
}