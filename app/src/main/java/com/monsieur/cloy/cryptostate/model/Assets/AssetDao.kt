package com.monsieur.cloy.cryptostate.model.Assets

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.monsieur.cloy.cryptostate.model.Prices.Price

@Dao
interface AssetDao {
    @Insert
    fun insertAsset(asset: Asset)

    @Insert
    fun insertAssets(assets: List<Asset>)

    @Query("DELETE FROM assets WHERE symbol = :symbol & asset = :asset")
    fun deleteAsset(symbol: String, asset: String)

    @Query("SELECT * FROM assets")
    fun getAllAssets(): LiveData<List<Asset>>

    @Update
    fun updateAsset(asset: Asset)

    @Update
    fun updateAssets(assets: List<Asset>)
}