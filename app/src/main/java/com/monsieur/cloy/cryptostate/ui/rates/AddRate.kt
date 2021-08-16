package com.monsieur.cloy.cryptostate.ui.rates

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import com.monsieur.cloy.cryptostate.R
import com.monsieur.cloy.cryptostate.databinding.FragmentAddRateBinding
import com.monsieur.cloy.cryptostate.model.Rates.Rate
import com.monsieur.cloy.cryptostate.utilits.*
import com.monsieur.cloy.cryptostate.viewModels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.net.HttpURLConnection
import java.net.URL

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
                val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
                viewModel.addRate(Rate(binding.symbol.text.trim().toString(), spinnerTextToCurrency(), spinnerTextToCategory()))
                backButton()
            }
        }
    }

    private fun spinnerTextToCurrency(): Rate.Companion.Currency{
        return when(binding.spinnerCurrency.selectedItemId.toInt()){
            0 -> Rate.Companion.Currency.USD
            1 -> Rate.Companion.Currency.EUR
            2 -> Rate.Companion.Currency.RUB
            3 -> Rate.Companion.Currency.UAH
            4 -> Rate.Companion.Currency.ConventionalUnit
            else -> Rate.Companion.Currency.USD
        }
    }

    private fun spinnerTextToCategory(): Rate.Companion.Categories{
        return when(binding.spinnerCurrency.selectedItemId.toInt()){
            0 -> Rate.Companion.Categories.Fiat
            1 -> Rate.Companion.Categories.Crypto
            2 -> Rate.Companion.Categories.Stock
            3 -> Rate.Companion.Categories.Bonds
            4 -> Rate.Companion.Categories.Other
            else -> Rate.Companion.Categories.Fiat
        }
    }


}