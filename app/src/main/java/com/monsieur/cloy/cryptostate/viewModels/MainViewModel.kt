package com.monsieur.cloy.cryptostate.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.monsieur.cloy.cryptostate.model.Assets.Asset
import com.monsieur.cloy.cryptostate.model.Assets.Assets
import com.monsieur.cloy.cryptostate.model.Prices.Price
import com.monsieur.cloy.cryptostate.model.Prices.Prices
import com.monsieur.cloy.cryptostate.model.Prices.UsdPrices
import com.monsieur.cloy.cryptostate.utilits.APP_ACTIVITY
import com.monsieur.cloy.cryptostate.utilits.Categories
import com.monsieur.cloy.cryptostate.utilits.FILE_NAME
import com.monsieur.cloy.cryptostate.utilits.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class MainViewModel: ViewModel() {

    val categories = MutableLiveData<List<Categories>>()
    var prices : MutableLiveData<Prices> = MutableLiveData()
    private var usdPrices = UsdPrices()
    var assets : MutableLiveData<Assets> = MutableLiveData()
    private var dataController = DataController()



    fun refresh(){
        GlobalScope.launch {
            if(updateUsdPrices() && updatePrices() && updateAssets()){
                Log.d("text", "Complete refresh")
            }
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
        val newPrice = prices.value!!
        newPrice.updatePrices(usdPrices)
        if(newPrice.ifLastUpdateError){
            GlobalScope.launch(Dispatchers.Main){
                showToast("Ошибка при обновлении данных курсов")
            }
            return false
        }
        Log.d("text", "Complete update prices")
        GlobalScope.launch(Dispatchers.Main){
            prices.value = newPrice
        }
        return true
    }

    private fun updateAssets(): Boolean{
        if(assets.value == null || assets.value!!.items.size == 0 || prices.value != null){
            return false
        }
        val newAssets = assets.value!!
        newAssets.updateAssets(prices.value!!)
        if(newAssets.ifLastUpdateError){
            GlobalScope.launch(Dispatchers.Main){
                showToast("Ошибка при обновлении данных активов")
            }
            return false
        }
        Log.d("text", "Complete update assets")
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
                Log.d("myExeptions", "ошибка при загрузке prices из JSON или их просто ещё нету")
            }
        }
    }

    fun saveData(){
        GlobalScope.launch {
            if (dataController.saveData()) {
                Log.d("myExeptions", "ошибка при сохрнении Prices")
            }
        }
    }



    private inner class DataController(){
        private var streamReader: InputStreamReader? = null
        private var fileInputStream: FileInputStream? = null
        private var fileOutputStream: FileOutputStream? = null


        fun loadData(): Boolean{
            if(!getDataFromJson()){
                prices.value = Prices()
                assets.value = Assets()
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
                prices.value = dataItems.prices
                assets.value = dataItems.assets
                return true
            }catch (e:Exception){
                e.message?.let { Log.d("myExeption", it) }
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