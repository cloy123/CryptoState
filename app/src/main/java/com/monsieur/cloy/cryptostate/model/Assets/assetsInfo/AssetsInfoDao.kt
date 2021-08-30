package com.monsieur.cloy.cryptostate.model.Assets.assetsInfo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.monsieur.cloy.cryptostate.model.Prices.Price

@Dao
interface AssetsInfoDao {

    @Insert
    fun insertAssetsInfo(assetsInfo: AssetsInfo)

    @Query("SELECT * FROM assetsInfo")
    fun getAssetsInfo(): LiveData<List<AssetsInfo>>

    @Update
    fun updateAssetsInfo(assetsInfo: AssetsInfo)
}