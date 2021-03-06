package com.monsieur.cloy.cryptostate.model.Prices

import android.util.Log
import com.monsieur.cloy.cryptostate.utilits.myExeptionsTag

class Prices {

    var items: ArrayList<Price> = ArrayList()
    var ifLastUpdateError = false
    var usdPrices: UsdPrices = UsdPrices()

    fun updatePrices(usdPrices: UsdPrices){
        ifLastUpdateError = false
        this.usdPrices = usdPrices
        for(price in items){
            price.updateCurrency(usdPrices)
            if(price.ifLastUpdateError){
                ifLastUpdateError = true
                Log.d(myExeptionsTag, price.symbol)
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

    fun findPrice(symbolName: String):Price?{
        for(price in items){
            if(price.symbolName == symbolName){
                return price
            }
        }
        return null
    }
}