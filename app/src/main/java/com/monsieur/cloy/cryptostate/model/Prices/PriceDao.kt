package com.monsieur.cloy.cryptostate.model.Prices

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PriceDao {

    @Insert
    fun insertPrice(price: Price)

    @Insert
    fun insertPrices(prices: List<Price>)

    @Delete
    fun deletePrice(price: Price)

    @Query("SELECT * FROM prices")
    fun getAllPrices(): LiveData<List<Price>>

    @Query("SELECT * FROM prices WHERE symbolName = :symbolName")
    fun findPrice(symbolName: String): List<Price>

    @Update
    fun updatePrice(price: Price)

    @Update
    fun updatePrices(prices: List<Price>)
}