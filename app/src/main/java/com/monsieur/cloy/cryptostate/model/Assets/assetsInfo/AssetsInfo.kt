package com.monsieur.cloy.cryptostate.model.Assets.assetsInfo

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assetsInfo")
class AssetsInfo {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "assetsInfoId")
    var id = 0

    var changeRUB = 0f
    var changeUSD = 0f
    var changeEUR = 0f
    var changeUAH = 0f

    var quantityRUB = 0f
    var quantityUSD = 0f
    var quantityEUR = 0f
    var quantityUAH = 0f
}