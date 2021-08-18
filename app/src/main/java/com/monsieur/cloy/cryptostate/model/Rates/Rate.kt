package com.monsieur.cloy.cryptostate.model.Rates

import android.util.Log
import com.monsieur.cloy.cryptostate.utilits.url
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.util.*
import com.monsieur.cloy.cryptostate.utilits.Currency
import com.monsieur.cloy.cryptostate.utilits.Categories

class Rate(var symbol: String, private var mainCurrency: Currency, var category: Categories) {

    var ifLastUpdateError: Boolean = false

    var rateUSD: Float = 0f
    var rateEUR: Float = 0f
    var rateRUB: Float = 0f
    var rateUAH: Float = 0f
    private var conventionalUnitRate: Float = 0f

    fun getMainRate(): Float {
        return when (mainCurrency) {
            Currency.RUB -> rateRUB
            Currency.USD -> rateUSD
            Currency.EUR -> rateEUR
            Currency.UAH -> rateUAH
            Currency.ConventionalUnit -> conventionalUnitRate
        }
    }

    fun setMainRate(rate: Float) {
        when (mainCurrency) {
            Currency.RUB -> rateRUB = rate
            Currency.USD -> rateUSD = rate
            Currency.EUR -> rateEUR = rate
            Currency.UAH -> rateUAH = rate
            Currency.ConventionalUnit -> conventionalUnitRate = rate
        }
    }

    fun UpdateCurrency(usdRates: UsdRates) {
        UpdateMainCurrency()
        if(ifLastUpdateError || mainCurrency == Currency.ConventionalUnit){
            return
        }

        if(mainCurrency == Currency.USD){
            rateRUB = rateUSD * usdRates.rateUsdRub
            rateEUR = rateUSD * usdRates.rateUsdEur
            rateUAH = rateUSD * usdRates.rateUsdUah
        }else if(mainCurrency == Currency.EUR){
            rateUSD = rateEUR / usdRates.rateUsdEur
            rateRUB = rateUSD * usdRates.rateUsdRub
            rateUAH = rateUSD * usdRates.rateUsdUah
        }else if(mainCurrency == Currency.RUB){
            rateUSD = rateRUB / usdRates.rateUsdRub
            rateEUR = rateUSD * usdRates.rateUsdEur
            rateUAH = rateUSD * usdRates.rateUsdUah
        }else if(mainCurrency == Currency.UAH){
            rateUSD = rateUAH / usdRates.rateUsdUah
            rateRUB = rateUSD * usdRates.rateUsdRub
            rateEUR = rateUSD * usdRates.rateUsdEur
        }
    }



    fun UpdateMainCurrency() {
        try {
            val doc = Jsoup.connect(url + symbol).get().body()
            var rateText = doc.select("div[class=YMlKec fxKbKc]").text()
            rateText = rateText.replace(",", "")
            setMainRate(rateText.toFloat())
            Log.d("text", "setMainRate = $rateText")
            ifLastUpdateError = false
        } catch (e: Exception) {
            if (e.message != null) {
                Log.d("myExeptions", e.message!! + " ошибка в Rate.kt в UpdateMainCurrency")
            } else {
                Log.d("myExeptions", "ошибка в Rate.kt в UpdateMainCurrency")
            }
            ifLastUpdateError = true
        }
    }
}