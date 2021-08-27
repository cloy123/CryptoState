package com.monsieur.cloy.cryptostate

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.monsieur.cloy.cryptostate.databinding.ActivityMainBinding
import com.monsieur.cloy.cryptostate.ui.assets.AddAsset
import com.monsieur.cloy.cryptostate.ui.main.MainFragment
import com.monsieur.cloy.cryptostate.ui.prices.AddPrice
import com.monsieur.cloy.cryptostate.utilits.*
import com.monsieur.cloy.cryptostate.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP_ACTIVITY = this
        replaceFragment(MainFragment(), false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.loadData()
        setSupportActionBar(binding.toolbar)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_fragment_menu, menu)
        val sharedPreferences = getSharedPreferences("theme", MODE_PRIVATE)
        if (menu != null) {
            if(sharedPreferences.getBoolean("dark", false)){
                val item = menu.getItem(1)
                item.icon = getDrawable(R.drawable.ic_round_light_mode_24)
            }
            toolbarMenu = menu
        }
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun changeTheme(){
        val sharedPreferences = getSharedPreferences("theme", MODE_PRIVATE)
        if(sharedPreferences.getBoolean("dark", false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            sharedPreferences.edit().putBoolean("light", true).apply()
            sharedPreferences.edit().putBoolean("dark", false).apply()
        }
        else if(sharedPreferences.getBoolean("light", false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            sharedPreferences.edit().putBoolean("dark", true).apply()
            sharedPreferences.edit().putBoolean("light", false).apply()
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            sharedPreferences.edit().putBoolean("light", true).apply()
            sharedPreferences.edit().putBoolean("dark", false).apply()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                backButton()
                true
            }
            R.id.action_mode -> {
                changeTheme()
                true
            }
            R.id.action_refresh -> {
                viewModel.refresh()
                true
            }
            R.id.action_add_price -> {
                replaceFragment(AddPrice())
                true
            }
            R.id.action_add_asset -> {
                replaceFragment(AddAsset())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}