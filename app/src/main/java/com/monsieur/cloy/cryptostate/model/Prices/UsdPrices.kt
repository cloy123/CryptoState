package com.monsieur.cloy.cryptostate.model.Prices

import android.util.Log
import com.monsieur.cloy.cryptostate.utilits.url
import org.jsoup.Jsoup

class UsdPrices {

    var ifLastUpdateError = false
    var priceUsdRub: Float = 1f
    var priceUsdEur: Float = 1f
    var priceUsdUah: Float = 1f

    fun updateUsdPrices(){
        ifLastUpdateError = !(updateUsdEurPrice() && updateUsdRubPrice() && updateUsdUahPrice())
    }

    private fun updateUsdUahPrice(): Boolean{
        try {
            val doc = Jsoup.connect(url + "USD-UAH").get().body()
            var priceText = doc.select("div[class=YMlKec fxKbKc]").text()
            priceText =  priceText.replace(",", "")
            priceUsdUah = priceText.toFloat()
            Log.d("text", "UsdUahPrice = $priceText")
            return true
        } catch (e: Exception) {
            if (e.message != null) {
                Log.d("myExeptions", e.message!!)
            } else {
                Log.d("myExeptions", "ошибка в UsdPrices.kt в UpdateUsdUahPrice")
            }
            return false
        }
    }

    private fun updateUsdEurPrice(): Boolean{
        try {
            val doc = Jsoup.connect(url + "USD-EUR").get().body()
            var priceText = doc.select("div[class=YMlKec fxKbKc]").text()
            priceText =  priceText.replace(",", "")
            priceUsdEur = priceText.toFloat()
            Log.d("text", "UsdEurPrice = $priceText")
            return true
        } catch (e: Exception) {
            if (e.message != null) {
                Log.d("myExeptions", e.message!!)
            } else {
                Log.d("myExeptions", "ошибка в UsdPrices.kt в UpdateUsdEurPrice")
            }
            return false
        }
    }

    private fun updateUsdRubPrice(): Boolean{
        try {
            val doc = Jsoup.connect(url + "USD-RUB").get().body()
            var priceText = doc.select("div[class=YMlKec fxKbKc]").text()
            priceText =  priceText.replace(",", "")
            priceUsdRub = priceText.toFloat()
            Log.d("text", "UsdRubPrice = $priceText")
            return true
        } catch (e: Exception) {
            if (e.message != null) {
                Log.d("myExeptions", e.message!!)
            } else {
                Log.d("myExeptions", "ошибка в UsdPrices.kt в UpdateUsdRubPrice")
            }
            return false
        }
    }
}