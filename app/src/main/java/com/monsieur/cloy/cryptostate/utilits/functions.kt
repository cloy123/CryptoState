package com.monsieur.cloy.cryptostate.utilits

import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.monsieur.cloy.cryptostate.R

fun replaceFragment(fragment: Fragment, addStack: Boolean = true) {
    /* Функция расширения для AppCompatActivity, позволяет устанавливать фрагменты */
    if (addStack) {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.container,
                fragment
            ).commit()
    } else {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                fragment
            ).commit()
    }
    //APP_ACTIVITY.findViewById<ConstraintLayout>(R.id.container).visibility = View.VISIBLE
}

fun addHomeButton(){
    val actionBar = APP_ACTIVITY.supportActionBar
    actionBar?.setHomeButtonEnabled(true)
    actionBar?.setDisplayHomeAsUpEnabled(true)
}
fun deleteHomeButton(){
    val actionBar = APP_ACTIVITY.supportActionBar
    actionBar?.setHomeButtonEnabled(false)
    actionBar?.setDisplayHomeAsUpEnabled(false)
}


fun backButton(){
    APP_ACTIVITY.onBackPressed()
}

fun showToast(message: String){
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_LONG).show()
}