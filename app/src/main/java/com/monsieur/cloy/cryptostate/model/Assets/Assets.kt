package com.monsieur.cloy.cryptostate.model.Assets

import android.util.Log
import android.widget.TextView
import com.monsieur.cloy.cryptostate.R
import com.monsieur.cloy.cryptostate.model.Prices.Price
import com.monsieur.cloy.cryptostate.model.Prices.Prices
import com.monsieur.cloy.cryptostate.utilits.Currency

class Assets {

    var items: ArrayList<Asset> = ArrayList()
    var ifLastUpdateError = false

    var changeRUB = 0f
    var changeUSD = 0f
    var changeEUR = 0f
    var changeUAH = 0f

    var quantityRUB = 0f
    var quantityUSD = 0f
    var quantityEUR = 0f
    var quantityUAH = 0f


    fun updateAssets(prices: Prices){
        quantityRUB = 0f
        quantityUSD = 0f
        quantityEUR = 0f
        quantityUAH = 0f
        changeRUB = 0f
        changeUSD = 0f
        changeEUR = 0f
        changeUAH = 0f
        for (asset in items){
            val price = findPrice(prices, asset.symbol)
            if(price != null){
                asset.update(price)
            }else{
                ifLastUpdateError = true
                Log.d("myExeptions", "не получилось найти в списке prices ${asset.symbol}")
            }
            quantityRUB += asset.quantityRUB
            quantityUSD += asset.quantityUSD
            quantityEUR += asset.quantityEUR
            quantityUAH += asset.quantityUAH
            changeUSD += prices.usdPrices.convert(asset.mainCurrency, Currency.USD, asset.change)
            changeRUB += prices.usdPrices.convert(asset.mainCurrency, Currency.RUB, asset.change)
            changeEUR += prices.usdPrices.convert(asset.mainCurrency, Currency.EUR, asset.change)
            changeUAH += prices.usdPrices.convert(asset.mainCurrency, Currency.UAH, asset.change)
        }
    }

    private fun findPrice(prices: Prices, symbol: String):Price?{
        for(price in prices.items){
            if(price.symbol == symbol){
                return price
            }
        }
        return null
    }
}