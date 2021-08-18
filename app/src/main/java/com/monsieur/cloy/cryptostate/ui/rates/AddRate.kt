package com.monsieur.cloy.cryptostate.ui.rates

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.monsieur.cloy.cryptostate.databinding.FragmentAddRateBinding
import com.monsieur.cloy.cryptostate.model.Rates.Rate
import com.monsieur.cloy.cryptostate.utilits.*
import com.monsieur.cloy.cryptostate.viewModels.RatesViewModel
import org.jsoup.Connection

class AddRate : Fragment() {

    private lateinit var _binding: FragmentAddRateBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddRateBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    var connection : Connection? = null

    override fun onStart() {
        super.onStart()
        addHomeButton()
        binding.cancelButton.setOnClickListener {
            backButton()
        }
        binding.addButton.setOnClickListener {
            if(binding.symbol.text.trim().isEmpty()){
                showToast("Поле символ - пусто")
                return@setOnClickListener
            }else{
                val viewModel = ViewModelProvider(requireActivity()).get(RatesViewModel::class.java)
                viewModel.addRate(Rate(binding.symbol.text.trim().toString(), spinnerTextToCurrency(), spinnerTextToCategory()))
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