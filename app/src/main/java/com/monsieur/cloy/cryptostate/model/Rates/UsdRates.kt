package com.monsieur.cloy.cryptostate.model.Rates

import android.util.Log
import com.monsieur.cloy.cryptostate.utilits.showToast
import com.monsieur.cloy.cryptostate.utilits.url
import org.jsoup.Jsoup

class UsdRates {

    var ifLastUpdateError = false
    var UsdRubRate: Float = 1f
    var UsdEurRate: Float = 1f
    var UsdUahRate: Float = 1f

    fun UpdateUsdRates(){
        if(UpdateUsdEurRate() && UpdateUsdRubRate() && UpdateUsdUahRate()){
            ifLastUpdateError = false
        }
        else{
            ifLastUpdateError = true
        }
    }

    private fun UpdateUsdUahRate(): Boolean{
        try {
            val doc = Jsoup.connect(url + "USD-UAH").get().body()
            var rateText = doc.select("div[class=YMlKec fxKbKc]").text()
            rateText =  rateText.replace(",", "")
            UsdUahRate = rateText.toFloat()
            Log.d("text", "UsdUahRate = $rateText")
            return true
        } catch (e: Exception) {
            if (e.message != null) {
                Log.d("myExeptions", e.message!!)
            } else {
                Log.d("myExeptions", "ошибка в UsdRates.kt в UpdateUsdUahRate")
            }
            return false
        }
    }

    private fun UpdateUsdEurRate(): Boolean{
        try {
            val doc = Jsoup.connect(url + "USD-EUR").get().body()
            var rateText = doc.select("div[class=YMlKec fxKbKc]").text()
            rateText =  rateText.replace(",", "")
            UsdEurRate = rateText.toFloat()
            Log.d("text", "UsdEurRate = $rateText")
            return true
        } catch (e: Exception) {
            if (e.message != null) {
                Log.d("myExeptions", e.message!!)
            } else {
                Log.d("myExeptions", "ошибка в UsdRates.kt в UpdateUsdEurRate")
            }
            return false
        }
    }

    private fun UpdateUsdRubRate(): Boolean{
        try {
            val doc = Jsoup.connect(url + "USD-RUB").get().body()
            var rateText = doc.select("div[class=YMlKec fxKbKc]").text()
            rateText =  rateText.replace(",", "")
            UsdRubRate = rateText.toFloat()
            Log.d("text", "UsdRubRate = $rateText")
            return true
        } catch (e: Exception) {
            if (e.message != null) {
                Log.d("myExeptions", e.message!!)
            } else {
                Log.d("myExeptions", "ошибка в UsdRates.kt в UpdateUsdRubRate")
            }
            return false
        }
    }
}