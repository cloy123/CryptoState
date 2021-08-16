package com.monsieur.cloy.cryptostate.utilits

import com.monsieur.cloy.cryptostate.MainActivity
import com.monsieur.cloy.cryptostate.R

lateinit var APP_ACTIVITY: MainActivity
val url get() = APP_ACTIVITY.getString(R.string.url)
val TRAINING_FILE_NAME = "rates.json"