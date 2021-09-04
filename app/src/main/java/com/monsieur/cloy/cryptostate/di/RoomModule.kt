package com.monsieur.cloy.cryptostate.di

import android.app.Application
import com.monsieur.cloy.cryptostate.base.AppDatabase
import com.monsieur.cloy.cryptostate.model.Assets.AssetDao
import com.monsieur.cloy.cryptostate.model.Assets.AssetRepository
import com.monsieur.cloy.cryptostate.model.Assets.assetsInfo.AssetsInfoDao
import com.monsieur.cloy.cryptostate.model.Assets.assetsInfo.AssetsInfoRepository
import com.monsieur.cloy.cryptostate.model.Prices.PriceDao
import com.monsieur.cloy.cryptostate.model.Prices.PriceRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(application: Application) {

    val appDatabase: AppDatabase = AppDatabase.getInstance(application)

    val application: Application = application


    @Singleton
    @Provides
    fun provideAppDatabase(): AppDatabase {
        return appDatabase
    }

    @Singleton
    @Provides
    fun providesPriceDao(appDatabase: AppDatabase): PriceDao{
        return appDatabase.priceDao()
    }

    @Singleton
    @Provides
    fun providesPriceRepository(priceDao: PriceDao): PriceRepository{
        return PriceRepository(priceDao)
    }

    @Singleton
    @Provides
    fun providesAssetDao(appDatabase: AppDatabase): AssetDao {
        return appDatabase.assetDao()
    }

    @Singleton
    @Provides
    fun providesAssetRepository(assetDao: AssetDao): AssetRepository{
        return AssetRepository(assetDao)
    }

    @Singleton
    @Provides
    fun providesAssetsInfoDao(appDatabase: AppDatabase): AssetsInfoDao{
        return appDatabase.assetsInfoDao()
    }

    @Singleton
    @Provides
    fun providesAssetsInfoRepository(assetsInfoDao: AssetsInfoDao): AssetsInfoRepository{
        return AssetsInfoRepository(assetsInfoDao)
    }
}