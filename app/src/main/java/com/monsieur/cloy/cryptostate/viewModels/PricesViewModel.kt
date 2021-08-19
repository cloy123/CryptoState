package com.monsieur.cloy.cryptostate.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.monsieur.cloy.cryptostate.model.Prices.Price
import com.monsieur.cloy.cryptostate.model.Prices.Prices
import com.monsieur.cloy.cryptostate.utilits.APP_ACTIVITY
import com.monsieur.cloy.cryptostate.utilits.FILE_NAME
import com.monsieur.cloy.cryptostate.utilits.showToast
import kotlinx.coroutines.*
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class PricesViewModel: ViewModel() {

    var prices : MutableLiveData<Prices> = MutableLiveData()

    private var streamReader: InputStreamReader? = null
    private var fileInputStream: FileInputStream? = null
    private var fileOutputStream: FileOutputStream? = null

    fun updatePrices(){
        GlobalScope.launch {
            if(prices.value != null && prices.value!!.items.size > 0){
                val newPrice = prices.value!!
                newPrice.updatePrices()
                if(newPrice.ifLastUpdateError){
                        GlobalScope.launch(Dispatchers.Main){
                        showToast("Ошибка при обновлении данных курсов")
                    }
                }
                Log.d("text", "Complete")
                GlobalScope.launch(Dispatchers.Main){
                    prices.value = newPrice
                    savePrices()
                }
            }
        }
    }

    fun removePrice(price: Price): Boolean{
        val newPrice = prices.value
        val result = newPrice?.remove(price)
        prices.value = newPrice
        return if(result == true){
            GlobalScope.launch {
                if(!savePrices()){
                    Log.d("myExeptions", "ошибка при сохрнении Prices")
                }
            }
            true
        }else{
            false
        }
    }

    fun loadPrices(){
        GlobalScope.launch {
            var loadedPrices = getPricesFromJson()
            if(loadedPrices == null){
                loadedPrices = Prices()
                Log.d("myExeptions", "ошибка при загрузке prices из JSON или их просто ещё нету")
            }
            GlobalScope.launch(Dispatchers.Main) {
                prices.value = loadedPrices
            }
        }
    }

    private fun getPricesFromJson():Prices?{
        try {
            fileInputStream = APP_ACTIVITY.openFileInput(FILE_NAME)
            streamReader = InputStreamReader(fileInputStream)
            val gson = Gson()
            val dataItems: DataItems = gson.fromJson(streamReader, DataItems::class.java)
            return dataItems.prices
        }catch (e:Exception){
            e.message?.let { Log.d("myExeption", it) }
            return null
        }

    }

    fun addPrice(price: Price){
        var newPrices = prices.value
        if(newPrices == null){
            newPrices = Prices()
        }
        newPrices.add(price)
        prices.value = newPrices
        GlobalScope.launch {
            if(!savePrices()){
                Log.d("myExeptions", "ошибка при сохрнении Prices")
            }
        }
    }

    private fun savePrices(): Boolean{
        fileOutputStream = null
        try {
            savePricesToJson()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            closeFileOutputStream()
        }
        return false
    }

    private fun savePricesToJson(){
        val gson = Gson()
        val dataItems = DataItems()
        dataItems.prices = prices.value
        val jsonString = gson.toJson(dataItems)
        fileOutputStream = APP_ACTIVITY.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
        fileOutputStream!!.write(jsonString.toByteArray())
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

    private class DataItems {
        var prices: Prices? = null
    }
}