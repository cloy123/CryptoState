package com.monsieur.cloy.cryptostate.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.monsieur.cloy.cryptostate.appComponent
import com.monsieur.cloy.cryptostate.model.Assets.Asset
import com.monsieur.cloy.cryptostate.model.Assets.AssetRepository
import com.monsieur.cloy.cryptostate.model.Assets.assetsInfo.AssetsInfo
import com.monsieur.cloy.cryptostate.model.Assets.assetsInfo.AssetsInfoRepository
import com.monsieur.cloy.cryptostate.model.Prices.Price
import com.monsieur.cloy.cryptostate.model.Prices.PriceRepository
import com.monsieur.cloy.cryptostate.model.Prices.UsdPrices
import com.monsieur.cloy.cryptostate.utilits.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val priceRepository: PriceRepository = application.appComponent.priceRepository
    val allPrices: LiveData<List<Price>>? = priceRepository.allPrices
    private val assetRepository: AssetRepository = application.appComponent.assetsRepository
    private val assetsInfoRepository: AssetsInfoRepository = application.appComponent.assetsInfoRepository
    val allAssets: LiveData<List<Asset>>? = assetRepository.allAssets
    val assetsInfo: LiveData<List<AssetsInfo>>? = assetsInfoRepository.assetsInfo
    private val usdPrices: UsdPrices = UsdPrices()

    init {
        allPrices?.observe(APP_ACTIVITY, Observer {
            //TODO переделать потом
            if(allPrices.value.isNullOrEmpty()){
                priceRepository.insertPrices(
                    listOf<Price>(
                        Price.getDefaultFiatPrice("USD", Currency.USD),
                        Price.getDefaultFiatPrice("RUB", Currency.RUB),
                        Price.getDefaultFiatPrice("EUR", Currency.EUR),
                        Price.getDefaultFiatPrice("UAH", Currency.UAH)))
            }
        })
    }

    fun refresh(){
        GlobalScope.launch {
            if(!updateUsdPrices()) {
                Log.d(myInfoTag, "Complete updateUsdPrices")
            }
            if(!updatePrices()) {
                Log.d(myInfoTag, "Complete updatePrices")
            }
            if(!updateAssets()){
                Log.d(myInfoTag, "Complete updateAssets")
                updateAssetsInfo()
            }
        }
    }

    private fun updateUsdPrices(): Boolean{
        usdPrices.updateUsdPrices()
        return usdPrices.ifLastUpdateError
    }

    private fun updatePrices(): Boolean{
        if(allPrices != null && allPrices.value != null) {
            return priceRepository.updatePrices(usdPrices)
        }else{
            return true
        }
    }

    private fun updateAssets(): Boolean{
        if(allPrices != null && allPrices.value != null){
            return assetRepository.updateAssets(allPrices.value!!)
        }else{
            return true
        }
    }

    private fun updateAssetsInfo(){
        assetsInfoRepository.updateAssetsInfo(allAssets?.value!!, usdPrices)
    }

    fun removeAsset(asset: Asset){
        assetRepository.deleteAsset(asset)
    }

    fun removePrice(price: Price) {
        priceRepository.deletePrice(price)
    }

    fun findPrice(symbolName: String): Price?{
        if(allPrices != null && allPrices.value != null){
            return PriceRepository.findPrice(allPrices.value!!, symbolName)
        }
        return null
    }

    fun addPrice(price: Price){
        priceRepository.insertPrice(price)
    }

    fun addAsset(asset: Asset){
        assetRepository.insertAsset(asset)
    }

    fun editAsset(asset: Asset){
        assetRepository.updateAsset(asset)
    }

    fun addAsset(assetName: String, priceSymbolName: String){
        if(allPrices != null && allPrices.value != null){
            val price = PriceRepository.findPrice(allPrices.value!!, priceSymbolName)
            val newAsset = price?.let { Asset(assetName, priceSymbolName, it.mainCurrency, it.isDefaultFiatPrice) }
            if(newAsset != null){
                addAsset(newAsset)
            }
        }
    }
}