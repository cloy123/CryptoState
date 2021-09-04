package com.monsieur.cloy.cryptostate.di

import android.app.Application
import com.monsieur.cloy.cryptostate.base.AppDatabase
import com.monsieur.cloy.cryptostate.model.Assets.AssetDao
import com.monsieur.cloy.cryptostate.model.Assets.AssetRepository
import com.monsieur.cloy.cryptostate.model.Assets.assetsInfo.AssetsInfoDao
import com.monsieur.cloy.cryptostate.model.Assets.assetsInfo.AssetsInfoRepository
import com.monsieur.cloy.cryptostate.model.Prices.PriceDao
import com.monsieur.cloy.cryptostate.model.Prices.PriceRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [], modules = [AppModule::class, RoomModule::class])
interface AppComponent {

    val application: Application

    val appDatabase: AppDatabase

    val assetsInfoDao: AssetsInfoDao
    val assetsInfoRepository: AssetsInfoRepository

    val priceDao: PriceDao
    val priceRepository: PriceRepository

    val assetDao: AssetDao
    val assetsRepository: AssetRepository
}