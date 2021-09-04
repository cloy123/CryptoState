package com.monsieur.cloy.cryptostate.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.monsieur.cloy.cryptostate.model.Assets.Asset
import com.monsieur.cloy.cryptostate.model.Assets.AssetDao
import com.monsieur.cloy.cryptostate.model.Assets.assetsInfo.AssetsInfo
import com.monsieur.cloy.cryptostate.model.Assets.assetsInfo.AssetsInfoDao
import com.monsieur.cloy.cryptostate.model.Prices.Price
import com.monsieur.cloy.cryptostate.model.Prices.PriceDao

@Database(entities =  [(Price::class), (Asset::class), (AssetsInfo::class)], version = 1)
abstract class AppDatabase(): RoomDatabase() {

    abstract fun priceDao(): PriceDao

    abstract fun assetDao(): AssetDao

    abstract fun assetsInfoDao(): AssetsInfoDao

    companion object{
        fun getInstance(context: Context): AppDatabase{
            return Room.databaseBuilder(context, AppDatabase::class.java, "mydatabase").build()
        }
    }
}