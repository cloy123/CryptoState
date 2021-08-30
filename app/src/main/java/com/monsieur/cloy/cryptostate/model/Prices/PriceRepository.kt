package com.monsieur.cloy.cryptostate.model.Prices

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.monsieur.cloy.cryptostate.base.AppDatabase
import com.monsieur.cloy.cryptostate.utilits.myExeptionsTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PriceRepository(application: Application) {

    val searchResult = MutableLiveData<List<Price>>()
    private var priceDao: PriceDao? = null
    val allPrices : LiveData<List<Price>>?

    var ifLastUpdateError = false

    init {
        val db = AppDatabase.getInstance(application)
        priceDao = db.priceDao()
        allPrices = priceDao?.getAllPrices()
    }

    fun updatePrices(usdPrices: UsdPrices){
        if(allPrices != null && allPrices.value != null){
            val newPrices = allPrices.value
            ifLastUpdateError = false
            for(price in newPrices!!){
                price.updateCurrency(usdPrices)
                if(price.ifLastUpdateError){
                    ifLastUpdateError = true
                    Log.d(myExeptionsTag, price.symbol)
                }
            }
            updatePrices(newPrices)
        }
    }

    private fun asyncFinished(result: List<Price>){
        GlobalScope.launch(Dispatchers.Main) {
            searchResult.value = result
        }
    }

    fun insertPrice(price: Price){
        GlobalScope.launch {
            priceDao?.insertPrice(price)
        }
    }

    fun isEmpty():Boolean{
        if(allPrices != null && allPrices.value != null){
            return allPrices.value!!.isEmpty()
        }
        return true
    }

    fun insetPrices(prices: List<Price>){
        GlobalScope.launch {
            priceDao?.insertPrices(prices)
        }
    }

    fun deletePrice(symbol: String, symbolName: String){
        GlobalScope.launch {
            priceDao?.deletePrice(symbol, symbolName)
        }
    }

    fun findPrice(symbolName: String){
        GlobalScope.launch {
            priceDao?.findPrice(symbolName)?.let { asyncFinished(it) }
        }
    }

    fun updatePrice(price: Price){
        GlobalScope.launch {
            priceDao?.updatePrice(price)
        }
    }

    private fun updatePrices(prices: List<Price>){
        GlobalScope.launch {
            priceDao?.updatePrices(prices)
        }
    }

    companion object{
        fun findPrice(prices: List<Price>, symbolName: String):Price?{
            for(price in prices){
                if(price.symbolName == symbolName){
                    return price
                }
            }
            return null
        }
    }
}