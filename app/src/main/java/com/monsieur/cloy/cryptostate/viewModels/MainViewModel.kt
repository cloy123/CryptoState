package com.monsieur.cloy.cryptostate.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.monsieur.cloy.cryptostate.R
import com.monsieur.cloy.cryptostate.model.Assets.Asset
import com.monsieur.cloy.cryptostate.model.Assets.Assets
import com.monsieur.cloy.cryptostate.model.Prices.Price
import com.monsieur.cloy.cryptostate.model.Prices.Prices
import com.monsieur.cloy.cryptostate.model.Prices.UsdPrices
import com.monsieur.cloy.cryptostate.utilits.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class MainViewModel: ViewModel() {

    var prices : MutableLiveData<Prices> = MutableLiveData()
    private var usdPrices = UsdPrices()
    var assets : MutableLiveData<Assets> = MutableLiveData()
    private var dataController = DataController()



    fun refresh(){
        GlobalScope.launch {
            if(updateUsdPrices()) {
                Log.d(myInfoTag, "Complete updateUsdPrices")
            }
            if(updatePrices()) {
                Log.d(myInfoTag, "Complete updatePrices")
            }
            if(updateAssets()){
                Log.d(myInfoTag, "Complete updateAssets")
            }
            saveData()
        }
    }

    fun saveAsset(asset: Asset){
        val newAssets = assets.value!!
            for(i in 0 until newAssets.items.size){
                if(newAssets.items[i].asset == asset.asset && newAssets.items[i].symbol == asset.symbol){
                    newAssets.items[i] = asset
                    assets.value = newAssets
                    saveData()
                    return
                }
            }
        newAssets.items.add(asset)
        assets.value = newAssets
        saveData()
    }

    fun saveAsset(assetName: String, priceSymbol: String){
        val price = prices.value?.findPrice(priceSymbol)
        val newAsset = price?.let { Asset(assetName, priceSymbol, it.mainCurrency) }
        if (newAsset != null) {
            saveAsset(newAsset)
        }
    }

    fun removeAsset(asset: Asset): Boolean{
        val newAssets = assets.value
        val result = newAssets?.remove(asset)
        assets.value = newAssets
        return if (result == true) {
            saveData()
            true
        } else {
            false
        }
    }

    private fun updateUsdPrices(): Boolean{
        usdPrices.updateUsdPrices()
        return !usdPrices.ifLastUpdateError
    }

    private fun updatePrices(): Boolean{
        if(prices.value == null || prices.value!!.items.size == 0){
            return false
        }
        var result = true
        val newPrice = prices.value!!
        newPrice.updatePrices(usdPrices)
        if(newPrice.ifLastUpdateError){
            GlobalScope.launch(Dispatchers.Main){
                showToast(APP_ACTIVITY.getString(R.string.error_updating_prices_data))
            }
            Log.d(myInfoTag, "error update prices")
            result = false
        }
        Log.d(myInfoTag, "Complete update prices")
        GlobalScope.launch(Dispatchers.Main){
            prices.value = newPrice
        }
        return result
    }

    private fun updateAssets(): Boolean{
        if(assets.value == null || assets.value!!.items.size == 0 || prices.value == null){
            return false
        }
        val newAssets = assets.value!!
        newAssets.updateAssets(prices.value!!)
        if(newAssets.ifLastUpdateError){
            GlobalScope.launch(Dispatchers.Main){
                showToast(APP_ACTIVITY.getString(R.string.error_updating_assets_data))
            }
            Log.d(myExeptionsTag, "error update assets")
            return false
        }
        Log.d(myInfoTag, "Complete update assets")
        GlobalScope.launch(Dispatchers.Main){
            assets.value = newAssets
        }
        return true
    }

    fun removePrice(price: Price): Boolean {
        val newPrice = prices.value
        val result = newPrice?.remove(price)
        prices.value = newPrice
        return if (result == true) {
            saveData()
            true
        } else {
            false
        }
    }

    fun addPrice(price: Price){
        var newPrices = prices.value
        if(newPrices == null){
            newPrices = Prices()
        }
        newPrices.add(price)
        prices.value = newPrices
        saveData()
    }



    fun loadData(){
        GlobalScope.launch {
            if(!dataController.loadData()){
                Log.d(myExeptionsTag, "ошибка при загрузке prices из JSON или их просто ещё нету")
            }
        }
    }

    fun saveData(){
        GlobalScope.launch {
            if (!dataController.saveData()) {
                Log.d(myExeptionsTag, "ошибка при сохрнении Prices")
            }
        }
    }

    private inner class DataController(){
        private var streamReader: InputStreamReader? = null
        private var fileInputStream: FileInputStream? = null
        private var fileOutputStream: FileOutputStream? = null


        fun loadData(): Boolean{
            if(!getDataFromJson()){
                GlobalScope.launch(Dispatchers.Main) {
                    prices.value = Prices()
                    assets.value = Assets()
                }
                return false
            }
            return true
        }

        private fun closeFileOutputStream(){
            if (fileOutputStream != null) {
                try {
                    fileOutputStream!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        fun getDataFromJson(): Boolean{
            try {
                fileInputStream = APP_ACTIVITY.openFileInput(FILE_NAME)
                streamReader = InputStreamReader(fileInputStream)
                val gson = Gson()
                val dataItems: DataItems = gson.fromJson(streamReader, DataItems::class.java)
                GlobalScope.launch(Dispatchers.Main) {
                    prices.value = dataItems.prices
                    assets.value = dataItems.assets
                }
                return true
            }catch (e:Exception){
                e.message?.let { Log.d(myExeptionsTag, it) }
                return false
            }
        }

        private fun saveDataToJson(){
            val gson = Gson()
            val dataItems = DataItems()
            dataItems.prices = prices.value
            dataItems.assets = assets.value
            val jsonString = gson.toJson(dataItems)
            fileOutputStream = APP_ACTIVITY.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            fileOutputStream!!.write(jsonString.toByteArray())
        }

        fun saveData(): Boolean{
            fileOutputStream = null
            try {
                saveDataToJson()
                return true
            } catch (e: Exception) {
                e.printStackTrace()
                e.message?.let { Log.d(myExeptionsTag, "$it  ошибка при сохранении") }
            } finally {
                closeFileOutputStream()
            }
            return false
        }

        private inner class DataItems {
            var prices: Prices? = null
            var assets: Assets? = null
        }
    }
}