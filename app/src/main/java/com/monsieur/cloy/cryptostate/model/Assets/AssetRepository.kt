package com.monsieur.cloy.cryptostate.model.Assets

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.monsieur.cloy.cryptostate.base.AppDatabase
import com.monsieur.cloy.cryptostate.model.Assets.assetsInfo.AssetsInfo
import com.monsieur.cloy.cryptostate.model.Assets.assetsInfo.AssetsInfoDao
import com.monsieur.cloy.cryptostate.model.Prices.*
import com.monsieur.cloy.cryptostate.utilits.Currency
import com.monsieur.cloy.cryptostate.utilits.myExeptionsTag
import com.monsieur.cloy.cryptostate.utilits.myInfoTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AssetRepository(application: Application) {

    var ifLastUpdateError = false

    private var assetDao: AssetDao? = null
    var allAssets : LiveData<List<Asset>>? = null
    private var assetsInfoDao: AssetsInfoDao? = null
    var assetsInfo: LiveData<List<AssetsInfo>>? = null

    init {
        val db = AppDatabase.getInstance(application)
        assetDao = db.assetDao()
        allAssets = assetDao?.getAllAssets()
        assetsInfoDao = db.assetsInfoDao()
        assetsInfo = assetsInfoDao?.getAssetsInfo()
    }

    @JvmName("updateAssets1")
    fun updateAssets(prices: List<Price>, usdPrices: UsdPrices){
        GlobalScope.launch {
            if (allAssets != null && allAssets!!.value != null) {
                val newAssetsInfo = AssetsInfo()
                val newAssets = allAssets!!.value

                for (asset in newAssets!!) {
                    val price = PriceRepository.findPrice(prices, asset.symbol)
                    if (price != null) {
                        asset.update(price)
                    } else {
                        ifLastUpdateError = true
                        Log.d(myExeptionsTag, "не получилось найти в списке prices ${asset.symbol}")
                    }
                    newAssetsInfo.quantityRUB += asset.quantityRUB
                    newAssetsInfo.quantityUSD += asset.quantityUSD
                    newAssetsInfo.quantityEUR += asset.quantityEUR
                    newAssetsInfo.quantityUAH += asset.quantityUAH
                    newAssetsInfo.changeUSD += usdPrices.convert(
                        asset.mainCurrency,
                        Currency.USD,
                        asset.change
                    )
                    newAssetsInfo.changeRUB += usdPrices.convert(
                        asset.mainCurrency,
                        Currency.RUB,
                        asset.change
                    )
                    newAssetsInfo.changeEUR += usdPrices.convert(
                        asset.mainCurrency,
                        Currency.EUR,
                        asset.change
                    )
                    newAssetsInfo.changeUAH += usdPrices.convert(
                        asset.mainCurrency,
                        Currency.UAH,
                        asset.change
                    )
                    Log.d(myInfoTag, "quantityRUB = $newAssetsInfo.quantityRUB")
                }
                //TODO как-то поменять или получить новое значение заново
                updateAssetsInfo(newAssetsInfo)
                updateAssets(newAssets)
            }
        }
    }

    fun isEmpty():Boolean{
        if(allAssets != null && allAssets!!.value != null){
            return allAssets!!.value!!.isEmpty()
        }
        return true
    }

    private fun updateAssetsInfo(assetsInfo: AssetsInfo){
        GlobalScope.launch {
            assetsInfoDao?.updateAssetsInfo(assetsInfo)
        }
    }

    fun insertAsset(asset: Asset){
        GlobalScope.launch {
            assetDao?.insertAsset(asset)
        }
    }

    fun insertAssets(assets: List<Asset>){
        GlobalScope.launch {
            assetDao?.insertAssets(assets)
        }
    }

    fun deleteAsset(symbol: String, asset: String){
        GlobalScope.launch {
            assetDao?.deleteAsset(symbol, asset)
        }
    }

    private fun updateAssets(assets: List<Asset>){
        GlobalScope.launch {
            assetDao?.updateAssets(assets)
        }
    }
}