package com.monsieur.cloy.cryptostate.model.Rates

import android.util.Log
import com.monsieur.cloy.cryptostate.utilits.url
import org.jsoup.Jsoup

class UsdRates {

    var ifLastUpdateError = false
    var rateUsdRub: Float = 1f
    var rateUsdEur: Float = 1f
    var rateUsdUah: Float = 1f

    fun updateUsdRates(){
        ifLastUpdateError = !(updateUsdEurRate() && updateUsdRubRate() && updateUsdUahRate())
    }

    private fun updateUsdUahRate(): Boolean{
        try {
            val doc = Jsoup.connect(url + "USD-UAH").get().body()
            var rateText = doc.select("div[class=YMlKec fxKbKc]").text()
            rateText =  rateText.replace(",", "")
            rateUsdUah = rateText.toFloat()
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

    private fun updateUsdEurRate(): Boolean{
        try {
            val doc = Jsoup.connect(url + "USD-EUR").get().body()
            var rateText = doc.select("div[class=YMlKec fxKbKc]").text()
            rateText =  rateText.replace(",", "")
            rateUsdEur = rateText.toFloat()
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

    private fun updateUsdRubRate(): Boolean{
        try {
            val doc = Jsoup.connect(url + "USD-RUB").get().body()
            var rateText = doc.select("div[class=YMlKec fxKbKc]").text()
            rateText =  rateText.replace(",", "")
            rateUsdRub = rateText.toFloat()
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