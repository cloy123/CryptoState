package com.monsieur.cloy.cryptostate.model.Assets

import androidx.lifecycle.LiveData
import androidx.room.*
import com.monsieur.cloy.cryptostate.model.Prices.Price

@Dao
interface AssetDao {
    @Insert
    fun insertAsset(asset: Asset)

    @Insert
    fun insertAssets(assets: List<Asset>)

    @Delete
    fun deleteAsset(asset: Asset)

    @Query("SELECT * FROM assets")
    fun getAllAssets(): LiveData<List<Asset>>

    @Update
    fun updateAsset(asset: Asset)

    @Update
    fun updateAssets(assets: List<Asset>)
}