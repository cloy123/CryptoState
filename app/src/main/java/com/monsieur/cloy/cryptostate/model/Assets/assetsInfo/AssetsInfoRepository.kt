package com.monsieur.cloy.cryptostate.model.Assets.assetsInfo

import android.util.Log
import androidx.lifecycle.LiveData
import com.monsieur.cloy.cryptostate.model.Assets.Asset
import com.monsieur.cloy.cryptostate.model.Prices.UsdPrices
import com.monsieur.cloy.cryptostate.utilits.Currency
import com.monsieur.cloy.cryptostate.utilits.myInfoTag
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AssetsInfoRepository @Inject constructor(val assetsInfoDao: AssetsInfoDao) {

    val assetsInfo: LiveData<List<AssetsInfo>> = assetsInfoDao.getAssetsInfo()

    fun updateAssetsInfo(assets: List<Asset>, usdPrices: UsdPrices){
        val newAssetsInfo = AssetsInfo()
        for(asset in assets) {
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
            Log.d(myInfoTag, "quantityRUB = ${newAssetsInfo.quantityRUB}")
        }
        if(assets.isNotEmpty()){
            insertAssetsInfo(newAssetsInfo)
        }
    }

    private fun insertAssetsInfo(assetsInfo: AssetsInfo){
        GlobalScope.launch {
            assetsInfoDao.insertAssetsInfo(assetsInfo)
        }
    }

}