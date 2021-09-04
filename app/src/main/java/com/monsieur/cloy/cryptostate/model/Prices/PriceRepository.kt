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
import javax.inject.Inject

class PriceRepository @Inject constructor (val priceDao: PriceDao) {

    val searchResult = MutableLiveData<List<Price>>()
    val allPrices : LiveData<List<Price>>?

    var ifLastUpdateError = false

    init {
        allPrices = priceDao.getAllPrices()
    }

    fun updatePrices(usdPrices: UsdPrices): Boolean{
        ifLastUpdateError = false
        if(allPrices != null && allPrices.value != null){
            val newPrices = allPrices.value
            for(price in newPrices!!){
                price.updateCurrency(usdPrices)
                if(price.ifLastUpdateError){
                    ifLastUpdateError = true
                    Log.d(myExeptionsTag, price.symbol)
                }
            }
            updatePrices(newPrices)
        }else{
            ifLastUpdateError = true
        }
        return ifLastUpdateError
    }

    private fun asyncFinished(result: List<Price>){
        GlobalScope.launch(Dispatchers.Main) {
            searchResult.value = result
        }
    }

    fun insertPrice(price: Price){
        GlobalScope.launch {
            priceDao.insertPrice(price)
        }
    }

    fun isEmpty():Boolean{
        if(allPrices != null && allPrices.value != null){
            return allPrices.value!!.isEmpty()
        }
        return true
    }

    fun insertPrices(prices: List<Price>){
        GlobalScope.launch {
            priceDao.insertPrices(prices)
        }
    }

    fun deletePrice(price: Price){
        GlobalScope.launch {
            priceDao.deletePrice(price)
        }
    }

    fun findPrice(symbolName: String){
        GlobalScope.launch {
            asyncFinished(priceDao.findPrice(symbolName))
        }
    }

    fun updatePrice(price: Price){
        GlobalScope.launch {
            priceDao.updatePrice(price)
        }
    }

    private fun updatePrices(prices: List<Price>){
        GlobalScope.launch {
            priceDao.updatePrices(prices)
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