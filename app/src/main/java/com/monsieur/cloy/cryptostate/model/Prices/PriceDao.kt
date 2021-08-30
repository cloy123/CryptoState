package com.monsieur.cloy.cryptostate.model.Prices

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PriceDao {

    @Insert
    fun insertPrice(price: Price)

    @Insert
    fun insertPrices(prices: List<Price>)

    @Query("DELETE FROM prices WHERE symbol = :symbol & symbolName = :symbolName")
    fun deletePrice(symbol: String, symbolName: String)

    @Query("SELECT * FROM prices")
    fun getAllPrices(): LiveData<List<Price>>

    @Query("SELECT * FROM prices WHERE symbolName = :symbolName")
    fun findPrice(symbolName: String): List<Price>

    @Update
    fun updatePrice(price: Price)

    @Update
    fun updatePrices(prices: List<Price>)
}