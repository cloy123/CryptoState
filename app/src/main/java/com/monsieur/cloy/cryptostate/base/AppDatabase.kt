package com.monsieur.cloy.cryptostate.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.monsieur.cloy.cryptostate.model.Assets.Asset
import com.monsieur.cloy.cryptostate.model.Assets.AssetDao
import com.monsieur.cloy.cryptostate.model.Assets.assetsInfo.AssetsInfoDao
import com.monsieur.cloy.cryptostate.model.Prices.Price
import com.monsieur.cloy.cryptostate.model.Prices.PriceDao

@Database(entities =  [(Price::class), (Asset::class)], version = 1)
abstract class AppDatabase(): RoomDatabase() {

    abstract fun priceDao(): PriceDao

    abstract fun assetDao(): AssetDao

    abstract fun assetsInfoDao(): AssetsInfoDao

    companion object{
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            if(instance == null){
                synchronized(AppDatabase::class.java){
                    if(instance == null){
                        instance = Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
                    }
                }
            }
            return instance!!
        }
    }
}