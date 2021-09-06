package com.monsieur.cloy.cryptostate.model.Assets

import android.util.Log
import androidx.lifecycle.LiveData
import com.monsieur.cloy.cryptostate.model.Prices.Price
import com.monsieur.cloy.cryptostate.model.Prices.PriceRepository
import com.monsieur.cloy.cryptostate.utilits.myExeptionsTag
import com.monsieur.cloy.cryptostate.utilits.myInfoTag
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AssetRepository @Inject constructor(val assetDao: AssetDao) {

    var ifLastUpdateError = false

    val allAssets : LiveData<List<Asset>> = assetDao.getAllAssets()

    @JvmName("updateAssets1")
    fun updateAssets(prices: List<Price>): Boolean{
        ifLastUpdateError = false
        if (allAssets.value != null) {
            val newAssets = allAssets.value

            for (asset in newAssets!!) {
                val price = PriceRepository.findPrice(prices, asset.symbol)
                if (price != null) {
                    asset.update(price)
                    Log.d(myInfoTag, asset.symbol)
                } else {
                    ifLastUpdateError = true
                    Log.d(myExeptionsTag, "не получилось найти в списке prices ${asset.symbol}")
                }

            }
            updateAssets(newAssets)
        }else{
            Log.d(myInfoTag, "update assets - ERROR2")
            ifLastUpdateError = true
        }
        return ifLastUpdateError
    }

    fun insertAsset(asset: Asset){
        GlobalScope.launch {
            assetDao.insertAsset(asset)
        }
    }

    fun insertAssets(assets: List<Asset>){
        GlobalScope.launch {
            assetDao.insertAssets(assets)
        }
    }

    fun deleteAsset(asset: Asset){
        GlobalScope.launch {
            assetDao.deleteAsset(asset)
        }
    }

    private fun updateAssets(assets: List<Asset>){
        GlobalScope.launch {
            assetDao.updateAssets(assets)
        }
    }

    fun updateAsset(asset: Asset){
        GlobalScope.launch {
            assetDao.updateAsset(asset)
        }
    }
}