package com.monsieur.cloy.cryptostate

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.monsieur.cloy.cryptostate.di.AppComponent
import com.monsieur.cloy.cryptostate.di.AppModule
import com.monsieur.cloy.cryptostate.di.DaggerAppComponent
import com.monsieur.cloy.cryptostate.di.RoomModule
import com.monsieur.cloy.cryptostate.model.Prices.Price
import com.monsieur.cloy.cryptostate.utilits.Currency

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .roomModule(RoomModule(this))
            .build()
        setTheme()
        addDefaultPrices()
    }

    fun addDefaultPrices(){
        val sharedPreferences = getSharedPreferences("DefaultPrices", MODE_PRIVATE)
        if(!sharedPreferences.getBoolean("added", false)){
            appComponent.priceRepository.insertPrices(listOf<Price>(
                Price.getDefaultFiatPrice("USD", Currency.USD),
                Price.getDefaultFiatPrice("RUB", Currency.RUB),
                Price.getDefaultFiatPrice("EUR", Currency.EUR),
                Price.getDefaultFiatPrice("UAH", Currency.UAH)))
            sharedPreferences.edit().putBoolean("added", true).apply()
        }
    }

    fun setTheme(){
        val sharedPreferences = getSharedPreferences("theme", MODE_PRIVATE)
        if(sharedPreferences.contains("dark")){
            if(sharedPreferences.getBoolean("dark", false)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
        else if(sharedPreferences.contains("light")){
            if (sharedPreferences.getBoolean("light", false)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            sharedPreferences.edit().putBoolean("light", true).apply()
        }
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }