package com.monsieur.cloy.cryptostate.model.Assets

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.monsieur.cloy.cryptostate.model.Prices.Price
import com.monsieur.cloy.cryptostate.model.Prices.UsdPrices
import com.monsieur.cloy.cryptostate.utilits.Categories
import com.monsieur.cloy.cryptostate.utilits.Currency
import kotlin.math.tan

@Entity(tableName = "assets")
class Asset {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "assetId")
    var id = 0

    var depositedQuantityMainCurrency = 0f
    var mainQuantity: Float = 0f
    var quantityUSD: Float = 0f
    var quantityEUR: Float = 0f
    var quantityRUB: Float = 0f
    var quantityUAH: Float = 0f
    var averagePrice: Float = 0f
    var change: Float = 0f
    var changeInUsd = 0f

    @ColumnInfo(name = "asset")
    var asset: String = ""
    @ColumnInfo(name = "symbol")
    var symbol: String = ""
    @ColumnInfo(name = "mainCurrency")
    var mainCurrency: Currency = Currency.USD
    var isDefaultFiatAsset: Boolean = false

    constructor(asset: String, symbol: String, mainCurrency: Currency, isDefaultFiatAsset: Boolean){
        this.asset = asset
        this.symbol = symbol
        this.mainCurrency = mainCurrency
        this.isDefaultFiatAsset = isDefaultFiatAsset
    }

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