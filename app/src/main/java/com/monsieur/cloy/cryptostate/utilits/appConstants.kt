package com.monsieur.cloy.cryptostate.utilits

import android.view.Menu
import com.monsieur.cloy.cryptostate.MainActivity
import com.monsieur.cloy.cryptostate.R
import com.monsieur.cloy.cryptostate.model.Prices.UsdPrices

lateinit var APP_ACTIVITY: MainActivity
var toolbarMenu: Menu? = null
val urlGoogleFinance get() = APP_ACTIVITY.getString(R.string.urlGoogleFinance)
val elementGoogleFinance = "div[class=YMlKec fxKbKc]"
val urlYahooFinance get() = APP_ACTIVITY.getString(R.string.urlYahooFinance)
val elementYahooFinance = "span[class=Trsdu(0.3s) Fw(b) Fz(36px) Mb(-4px) D(ib)]"
val FILE_NAME = "CryptoStateData.json"

val myExeptionsTag = "myExeptions"
val myInfoTag = "myInfo"
var currentTabPosition = 0