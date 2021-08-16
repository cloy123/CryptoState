package com.monsieur.cloy.cryptostate.ui.assets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monsieur.cloy.cryptostate.R

class PageMyAssetsState : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_page_my_assets_state, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PageMyAssetsState()
    }
}