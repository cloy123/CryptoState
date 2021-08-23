package com.monsieur.cloy.cryptostate.ui.assets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.monsieur.cloy.cryptostate.databinding.FragmentAddPriceBinding
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        changeToolBar(menu = false, homeButton = true, "Изменить курс")
        binding.cancelButton.setOnClickListener {
            backButton()
        }

        binding.buy.setOnClickListener {
            if(binding.buyPrice.text.trim().isEmpty() || binding.buyQuantity.text.trim().isEmpty()){
                showToast("Количество или цена не заполнено")
            }else{
                asset.buy(binding.buyQuantity.text.toString().toFloat(), binding.buyPrice.text.toString().toFloat(), price)
                updateFields()
            }
        }

        binding.sell.setOnClickListener {
            if(binding.sellPrice.text.trim().isEmpty() || binding.sellQuantity.text.trim().isEmpty()){
                showToast("Количество или цена не заполнено")
            }else{
                asset.sell(binding.sellQuantity.text.toString().toFloat(), binding.sellPrice.text.toString().toFloat(), price)
                updateFields()
            }
        }

        binding.saveButton.setOnClickListener {
            if(binding.assetName.text.trim().isEmpty()){
                showToast("Поле Название - пусто")
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
    }
}