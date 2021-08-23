package com.monsieur.cloy.cryptostate.model.Prices

import android.util.Log
import com.monsieur.cloy.cryptostate.utilits.*
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
        return try {
            val doc = Jsoup.connect(urlGoogleFinance + "USD-UAH").get().body()
            var priceText = doc.select(elementGoogleFinance).text()
            priceText =  priceText.replace(",", "")
            priceUsdUah = priceText.toFloat()
            Log.d(myInfoTag, "UsdUahPrice = $priceText")
            true
        } catch (e: Exception) {
            if (e.message != null) {
                Log.d(myExeptionsTag, e.message!!)
            } else {
                Log.d(myExeptionsTag, "ошибка в UsdPrices.kt в UpdateUsdUahPrice")
            }
            false
        }
    }

    fun convert(from: Currency, to: Currency, asset: Float): Float{
        val result = when(from){
            Currency.RUB -> asset / priceUsdRub
            Currency.USD -> asset
            Currency.EUR -> asset / priceUsdEur
            Currency.UAH -> asset / priceUsdUah
            else -> asset
        }
        return when(to){
            Currency.USD -> result
            Currency.EUR -> result * priceUsdEur
            Currency.RUB -> result * priceUsdRub
            Currency.UAH -> result * priceUsdUah
            else -> result
        }
    }

    private fun updateUsdEurPrice(): Boolean{
        try {
            val doc = Jsoup.connect(urlGoogleFinance + "USD-EUR").get().body()
            var priceText = doc.select(elementGoogleFinance).text()
            priceText =  priceText.replace(",", "")
            priceUsdEur = priceText.toFloat()
            Log.d(myInfoTag, "UsdEurPrice = $priceText")
            return true
        } catch (e: Exception) {
            if (e.message != null) {
                Log.d(myExeptionsTag, e.message!!)
            } else {
                Log.d(myExeptionsTag, "ошибка в UsdPrices.kt в UpdateUsdEurPrice")
            }
            return false
        }
    }

    private fun updateUsdRubPrice(): Boolean{
        try {
            val doc = Jsoup.connect(urlGoogleFinance + "USD-RUB").get().body()
            var priceText = doc.select(elementGoogleFinance).text()
            priceText =  priceText.replace(",", "")
            priceUsdRub = priceText.toFloat()
            Log.d(myInfoTag, "UsdRubPrice = $priceText")
            return true
        } catch (e: Exception) {
            if (e.message != null) {
                Log.d(myExeptionsTag, e.message!!)
            } else {
                Log.d(myExeptionsTag, "ошибка в UsdPrices.kt в UpdateUsdRubPrice")
            }
            return false
        }
    }
}