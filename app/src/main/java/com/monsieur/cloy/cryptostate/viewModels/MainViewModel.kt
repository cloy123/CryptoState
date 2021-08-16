package com.monsieur.cloy.cryptostate.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.monsieur.cloy.cryptostate.model.Rates.Rate
import com.monsieur.cloy.cryptostate.model.Rates.Rates
import com.monsieur.cloy.cryptostate.model.Rates.UsdRates
import com.monsieur.cloy.cryptostate.utilits.APP_ACTIVITY
import com.monsieur.cloy.cryptostate.utilits.TRAINING_FILE_NAME
import com.monsieur.cloy.cryptostate.utilits.showToast
import kotlinx.coroutines.*
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import kotlin.coroutines.CoroutineContext

class MainViewModel: ViewModel() {

    var rates : MutableLiveData<Rates> = MutableLiveData()

    fun updateRates(){
        GlobalScope.launch {
            if(rates.value != null && rates.value!!.items.size > 0){
                val newRates = rates.value!!
                newRates.UpdateRates()
                if(newRates.ifLastUpdateError){
                        GlobalScope.launch(Dispatchers.Main){
                        showToast("Ошибка при обновлении данных курсов")
                    }
                }
                Log.d("text", "Complete")
                GlobalScope.launch(Dispatchers.Main){
                    rates.value = newRates
                    saveRates()
                }
            }
        }
    }

    fun loadRates(){
        GlobalScope.launch {
            var loadedRates = getRatesFromJson()
            if(loadedRates == null){
                loadedRates = Rates()
                Log.d("myExeptions", "ошибка при загрузке rates из JSON или их просто ещё нету")
            }
            GlobalScope.launch(Dispatchers.Main) {
                rates.value = loadedRates
            }
        }
    }

    private fun getRatesFromJson():Rates?{
        try {
            fileInputStream = APP_ACTIVITY?.openFileInput(TRAINING_FILE_NAME)
            streamReader = InputStreamReader(fileInputStream)
            val gson = Gson()
            val dataItems: DataItems = gson.fromJson(streamReader, DataItems::class.java)
            return dataItems.rates
        }catch (e:Exception){
            e.message?.let { Log.d("myExeption", it) }
            return null
        }

    }

    fun addRate(rate: Rate){
        var newRates = rates.value
        if(newRates == null){
            newRates = Rates()
        }
        newRates.add(rate)
        rates.value = newRates
        GlobalScope.launch {
            if(!saveRates()){
                Log.d("myExeptions", "ошибка при сохрнении Rates")
            }
        }
    }

    private var streamReader: InputStreamReader? = null
    private var fileInputStream: FileInputStream? = null
    private var fileOutputStream: FileOutputStream? = null

    private fun saveRates(): Boolean{
        fileOutputStream = null
        try {
            saveRatesToJson()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            closeFileOutputStream()
        }
        return false
    }

    private fun saveRatesToJson(){
        val gson = Gson()
        val dataItems = DataItems()
        dataItems.rates = rates.value
        val jsonString = gson.toJson(dataItems)
        fileOutputStream = APP_ACTIVITY.openFileOutput(TRAINING_FILE_NAME, Context.MODE_PRIVATE)
        fileOutputStream!!.write(jsonString.toByteArray())
    }

    private fun closeStreamReader(){
        if (streamReader != null) {
            try {
                streamReader!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun closeFileInputStream(){
        if (fileInputStream != null) {
            try {
                fileInputStream!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
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
        var rates: Rates? = null
    }
}