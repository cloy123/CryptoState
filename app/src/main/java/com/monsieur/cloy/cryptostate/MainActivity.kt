package com.monsieur.cloy.cryptostate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.monsieur.cloy.cryptostate.databinding.ActivityMainBinding
import com.monsieur.cloy.cryptostate.ui.main.MainFragment
import com.monsieur.cloy.cryptostate.ui.main.ViewPagerAdapter
import com.monsieur.cloy.cryptostate.utilits.APP_ACTIVITY
import com.monsieur.cloy.cryptostate.utilits.backButton
import com.monsieur.cloy.cryptostate.utilits.replaceFragment
import com.monsieur.cloy.cryptostate.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP_ACTIVITY = this
    }

    override fun onStart() {
        super.onStart()
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.loadRates()
        replaceFragment(MainFragment(), false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                backButton()
                Toast.makeText(this, "dddsfdsfsfs", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}