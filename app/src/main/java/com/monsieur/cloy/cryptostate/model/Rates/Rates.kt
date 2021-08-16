package com.monsieur.cloy.cryptostate.model.Rates

import android.content.Context
import com.google.gson.Gson
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class Rates {

    var items: ArrayList<Rate> = ArrayList()
    var ifLastUpdateError = false
    var usdRates: UsdRates = UsdRates()

    fun UpdateRates(){
        usdRates.UpdateUsdRates()
        if(usdRates.ifLastUpdateError){
            ifLastUpdateError = true
            return
        }else{
            for(rate in items){
                rate.UpdateCurrency(usdRates)
                if(rate.ifLastUpdateError){
                    ifLastUpdateError = true
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

    companion object{
        fun load(context: Context, fileName: String): Rates{
            TODO()
//            val fileInputStream = context.openFileInput(fileName)
//            val streamReader = InputStreamReader(fileInputStream)
//            val gson = Gson()
        }
    }
}