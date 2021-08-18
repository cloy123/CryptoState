package com.monsieur.cloy.cryptostate.model.Rates

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class Rates {

    var items: ArrayList<Rate> = ArrayList()
    var ifLastUpdateError = false
    var usdRates: UsdRates = UsdRates()

    fun updateRates(){
        ifLastUpdateError = false
        usdRates.updateUsdRates()
        if(usdRates.ifLastUpdateError){
            ifLastUpdateError = true
            Log.d("myExeptions", "usdRates")
            return
        }else{
            for(rate in items){
                rate.UpdateCurrency(usdRates)
                if(rate.ifLastUpdateError){
                    ifLastUpdateError = true
                    Log.d("myExeptions", rate.symbol)
                }
            }
        }
    }

    fun add(rate: Rate){
        items.add(rate)
    }
    fun remove(rate: Rate): Boolean{
        return items.remove(rate)
    }

    fun isEmpty():Boolean{
        if(items.size > 0){
            return false
        }
        return true
    }
}