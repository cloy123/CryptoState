package com.monsieur.cloy.cryptostate.model.Prices

import android.util.Log

class Prices {

    var items: ArrayList<Price> = ArrayList()
    var ifLastUpdateError = false
    var usdPrices: UsdPrices = UsdPrices()

    fun updatePrices(usdPrices: UsdPrices){
        ifLastUpdateError = false
        this.usdPrices = usdPrices
        for(price in items){
            price.UpdateCurrency(usdPrices)
            if(price.ifLastUpdateError){
                ifLastUpdateError = true
                Log.d("myExeptions", price.symbol)
            }
        }
    }

    fun add(price: Price){
        items.add(price)
    }
    fun remove(price: Price): Boolean{
        return items.remove(price)
    }

    fun isEmpty():Boolean{
        if(items.size > 0){
            return false
        }
        return true
    }

    fun findPrice(symbol: String):Price?{
        for(price in items){
            if(price.symbol == symbol){
                return price
            }
        }
        return null
    }
}