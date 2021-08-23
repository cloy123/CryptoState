package com.monsieur.cloy.cryptostate.model.Prices

import android.util.Log
import org.jsoup.Jsoup
import com.monsieur.cloy.cryptostate.utilits.Currency
import com.monsieur.cloy.cryptostate.utilits.Categories
import com.monsieur.cloy.cryptostate.utilits.myExeptionsTag
import com.monsieur.cloy.cryptostate.utilits.myInfoTag

class Price(var symbol: String, var symbolName: String, var mainCurrency: Currency, var category: Categories, val url: String, val element: String) {

    var ifLastUpdateError: Boolean = false

    var priceUSD: Float = 0f
    var priceEUR: Float = 0f
    var priceRUB: Float = 0f
    var priceUAH: Float = 0f
    private var conventionalUnitPrice: Float = 0f

    fun getMainPrice(): Float {
        return when (mainCurrency) {
            Currency.RUB -> priceRUB
            Currency.USD -> priceUSD
            Currency.EUR -> priceEUR
            Currency.UAH -> priceUAH
            Currency.ConventionalUnit -> conventionalUnitPrice
        }
    }

    fun setMainPrice(price: Float) {
        when (mainCurrency) {
            Currency.RUB -> priceRUB = price
            Currency.USD -> priceUSD = price
            Currency.EUR -> priceEUR = price
            Currency.UAH -> priceUAH = price
            Currency.ConventionalUnit -> conventionalUnitPrice = price
        }
    }

    fun UpdateCurrency(usdPrices: UsdPrices) {
        UpdateMainCurrency()
        if(ifLastUpdateError || mainCurrency == Currency.ConventionalUnit){
            return
        }

        if(mainCurrency == Currency.USD){
            priceRUB = priceUSD * usdPrices.priceUsdRub
            priceEUR = priceUSD * usdPrices.priceUsdEur
            priceUAH = priceUSD * usdPrices.priceUsdUah
        }else if(mainCurrency == Currency.EUR){
            priceUSD = priceEUR / usdPrices.priceUsdEur
            priceRUB = priceUSD * usdPrices.priceUsdRub
            priceUAH = priceUSD * usdPrices.priceUsdUah
        }else if(mainCurrency == Currency.RUB){
            priceUSD = priceRUB / usdPrices.priceUsdRub
            priceEUR = priceUSD * usdPrices.priceUsdEur
            priceUAH = priceUSD * usdPrices.priceUsdUah
        }else if(mainCurrency == Currency.UAH){
            priceUSD = priceUAH / usdPrices.priceUsdUah
            priceRUB = priceUSD * usdPrices.priceUsdRub
            priceEUR = priceUSD * usdPrices.priceUsdEur
        }
    }



    fun UpdateMainCurrency() {
        try {
            val doc = Jsoup.connect(url + symbol).get().body()
            var priceText = doc.select(element).text()
            priceText = priceText.replace("[^0-9.]".toRegex(), "")

            setMainPrice(priceText.toFloat())
            Log.d(myInfoTag, "setMainPrice = $priceText")
            ifLastUpdateError = false
        } catch (e: Exception) {
            if (e.message != null) {
                Log.d(myExeptionsTag, e.message!! + " ошибка в Price.kt в UpdateMainCurrency")
            } else {
                Log.d(myExeptionsTag, "ошибка в Price.kt в UpdateMainCurrency")
            }
            ifLastUpdateError = true
        }
    }
}