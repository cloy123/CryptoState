package com.monsieur.cloy.cryptostate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.monsieur.cloy.cryptostate.databinding.ActivityMainBinding
import com.monsieur.cloy.cryptostate.ui.main.MainFragment
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_fragment_menu, menu)
        if (menu != null) {
            toolbarMenu = menu
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                backButton()
                true
            }
            R.id.action_refresh -> {
                viewModel.refresh()
                true
            }
            R.id.action_settings -> {
                showToast("ну типо настройки")//TODO
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}