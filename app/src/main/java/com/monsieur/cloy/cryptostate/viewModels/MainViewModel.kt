package com.monsieur.cloy.cryptostate.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.monsieur.cloy.cryptostate.model.Rates.Rate
import com.monsieur.cloy.cryptostate.utilits.Categories

class MainViewModel: ViewModel() {

    val categories = MutableLiveData<List<Categories>>()
}