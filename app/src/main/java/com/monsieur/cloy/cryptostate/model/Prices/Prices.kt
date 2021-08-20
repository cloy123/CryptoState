package com.monsieur.cloy.cryptostate.model.Prices

import android.util.Log

class Prices {

    var items: ArrayList<Price> = ArrayList()
    var ifLastUpdateError = false
    var usdPrices: UsdPrices = UsdPrices()

    fun updatePrices(usdPrices: UsdPrices){
        ifLastUpdateError = false
        this.usdPrices = usdPrices
        if(usdPrices.ifLastUpdateError){
            ifLastUpdateError = true
            Log.d("myExeptions", "usdPrices")
            return
        }else{
            for(price in items){
                price.UpdateCurrency(usdPrices)
                if(price.ifLastUpdateError){
                    ifLastUpdateError = true
                    Log.d("myExeptions", price.symbol)
                }
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
}