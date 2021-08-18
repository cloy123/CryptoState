package com.monsieur.cloy.cryptostate.model.Assets

import com.monsieur.cloy.cryptostate.utilits.Categories
import com.monsieur.cloy.cryptostate.utilits.Currency

class Asset(var asset: String, var symbol: String, val categories: Categories, private var mainCurrency: Currency) {

    var mainQuantity: Float = 0f
    var quantityUSD: Float = 0f
    var quantityEUR: Float = 0f
    var quantityRUB: Float = 0f
    var quantityUAH: Float = 0f
    var averagePrice: Float = 0f
    var change: Float = 0f
}