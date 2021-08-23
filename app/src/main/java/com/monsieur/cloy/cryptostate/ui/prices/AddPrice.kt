package com.monsieur.cloy.cryptostate.ui.prices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.monsieur.cloy.cryptostate.R
import com.monsieur.cloy.cryptostate.databinding.FragmentAddPriceBinding
import com.monsieur.cloy.cryptostate.model.Prices.Price
import com.monsieur.cloy.cryptostate.utilits.*
import com.monsieur.cloy.cryptostate.viewModels.MainViewModel
import org.jsoup.Connection

class AddPrice : Fragment() {

    private lateinit var _binding: FragmentAddPriceBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPriceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        addHomeButton()
        binding.cancelButton.setOnClickListener {
            backButton()
        }
        binding.addButton.setOnClickListener {
            if(binding.symbol.text.trim().isEmpty() || binding.symbolName.text.trim().isEmpty()){
                showToast("Поле символ - пусто")
                return@setOnClickListener
            }else{
                val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
                val site = resources.getStringArray(R.array.sites)[ binding.spinnerUrl.selectedItemPosition]
                val element = getElement(site)
                viewModel.addPrice(
                    Price(
                        binding.symbol.text.trim().toString(),
                        binding.symbolName.text.trim().toString(),
                        spinnerTextToCurrency(),
                        spinnerTextToCategory(),
                        site,
                        element))
                backButton()
            }
        }
    }



    private fun spinnerTextToCurrency(): Currency{
        return when(binding.spinnerCurrency.selectedItemId.toInt()){
            0 -> Currency.USD
            1 -> Currency.EUR
            2 -> Currency.RUB
            3 -> Currency.UAH
            4 -> Currency.ConventionalUnit
            else -> Currency.USD
        }
    }

    private fun spinnerTextToCategory(): Categories{
        return when(binding.spinnerCurrency.selectedItemId.toInt()){
            0 -> Categories.Fiat
            1 -> Categories.Crypto
            2 -> Categories.Stock
            3 -> Categories.Bonds
            4 -> Categories.Other
            else -> Categories.Fiat
        }
    }


}