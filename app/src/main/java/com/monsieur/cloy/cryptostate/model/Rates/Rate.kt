package com.monsieur.cloy.cryptostate.model.Rates

import android.util.Log
import com.monsieur.cloy.cryptostate.utilits.url
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.util.*

class Rate(symbol: String, mainCurrency: Currency, category: Categories) {

    var ifLastUpdateError: Boolean = false

    var Symbol: String = symbol
    var MainCurrency: Currency = mainCurrency
    var Category: Categories = category

    var USDRate: Float = 0f
    var EURRate: Float = 0f
    var RUBRate: Float = 0f
    var UAHRate: Float = 0f
    var ConventionalUnitRate: Float = 0f

    fun getMainRate(): Float {
        return when (MainCurrency) {
            Companion.Currency.RUB -> RUBRate
            Companion.Currency.USD -> USDRate
            Companion.Currency.EUR -> EURRate
            Companion.Currency.UAH -> UAHRate
            Currency.ConventionalUnit -> ConventionalUnitRate
        }
    }

    fun setMainRate(rate: Float) {
        when (MainCurrency) {
            Companion.Currency.RUB -> RUBRate = rate
            Companion.Currency.USD -> USDRate = rate
            Companion.Currency.EUR -> EURRate = rate
            Companion.Currency.UAH -> UAHRate = rate
            Currency.ConventionalUnit -> ConventionalUnitRate = rate
        }
    }

    companion object {
        enum class Currency {
            USD,
            EUR,
            RUB,
            UAH,
            ConventionalUnit
        }

        enum class Categories {
            Fiat,
            Crypto,
            Stock,
            Bonds,
            Other
        }
    }

    fun UpdateCurrency(usdRates: UsdRates) {
        UpdateMainCurrency()
        if(ifLastUpdateError || MainCurrency == Currency.ConventionalUnit){
            return
        }

        if(MainCurrency == Currency.USD){
            RUBRate = USDRate * usdRates.UsdRubRate
            EURRate = USDRate * usdRates.UsdEurRate
            UAHRate = USDRate * usdRates.UsdUahRate
        }else if(MainCurrency == Currency.EUR){
            USDRate = EURRate / usdRates.UsdEurRate
            RUBRate = USDRate * usdRates.UsdRubRate
            UAHRate = USDRate * usdRates.UsdUahRate
        }else if(MainCurrency == Currency.RUB){
            USDRate = RUBRate / usdRates.UsdRubRate
            EURRate = USDRate * usdRates.UsdEurRate
            UAHRate = USDRate * usdRates.UsdUahRate
        }else if(MainCurrency == Currency.UAH){
            USDRate = UAHRate / usdRates.UsdUahRate
            RUBRate = USDRate * usdRates.UsdRubRate
            EURRate = USDRate * usdRates.UsdEurRate
        }
    }



    fun UpdateMainCurrency() {
        try {
            val doc = Jsoup.connect(url + Symbol).get().body()
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