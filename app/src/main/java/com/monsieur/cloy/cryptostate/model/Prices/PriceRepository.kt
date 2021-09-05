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

    fun insertPrice(price: Price){
        GlobalScope.launch {
            priceDao.insertPrice(price)
        }
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