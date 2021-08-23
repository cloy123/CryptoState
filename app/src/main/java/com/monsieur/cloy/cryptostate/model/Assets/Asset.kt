package com.monsieur.cloy.cryptostate.model.Assets

import com.monsieur.cloy.cryptostate.model.Prices.Price
import com.monsieur.cloy.cryptostate.model.Prices.UsdPrices
import com.monsieur.cloy.cryptostate.utilits.Categories
import com.monsieur.cloy.cryptostate.utilits.Currency

class Asset(var asset: String, var symbol: String, var mainCurrency: Currency) {

    var depositedQuantityMainCurrency = 0f
    var mainQuantity: Float = 0f
    var quantityUSD: Float = 0f
    var quantityEUR: Float = 0f
    var quantityRUB: Float = 0f
    var quantityUAH: Float = 0f
    var averagePrice: Float = 0f
    var change: Float = 0f
    var changeInUsd = 0f

    fun buy(quantity: Float, buyPrice: Float, price:  Price){
        depositedQuantityMainCurrency += quantity*buyPrice
        mainQuantity += quantity
        update(price)
    }

    fun sell(quantity: Float, sellPrice: Float, price: Price){
        depositedQuantityMainCurrency -= quantity*sellPrice
        mainQuantity -= quantity
        update(price)
    }

    fun update(price:  Price){
        averagePrice = depositedQuantityMainCurrency / mainQuantity
        quantityUSD = price.priceUSD * mainQuantity
        quantityEUR = price.priceEUR * mainQuantity
        quantityRUB = price.priceRUB * mainQuantity
        quantityUAH = price.priceUAH * mainQuantity
        change = getMainPrice(price.mainCurrency) - depositedQuantityMainCurrency
    }

    private fun getMainPrice(mainCurrency: Currency): Float {
        return when (mainCurrency) {
            Currency.RUB -> quantityRUB
            Currency.USD -> quantityUSD
            Currency.EUR -> quantityEUR
            Currency.UAH -> quantityUAH
            else -> mainQuantity
        }
    }
}