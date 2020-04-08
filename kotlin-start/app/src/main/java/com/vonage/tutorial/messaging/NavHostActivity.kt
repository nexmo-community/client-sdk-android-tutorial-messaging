package com.vonage.tutorial.messaging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.vonage.tutorial.R
import com.vonage.tutorial.messaging.extension.currentNavigationFragment
import kotlinx.android.synthetic.main.activity_nav_host.*

class NavHostActivity : AppCompatActivity(R.layout.activity_nav_host) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbarTitle()
    }

    private fun initToolbarTitle() {
        val host = navHostFragment as NavHostFragment
        val navController = host.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = destination.label
        }
    }

    override fun onBackPressed() {
        val currentNavigationFragment = supportFragmentManager.currentNavigationFragment as? BackPressHandler
        currentNavigationFragment?.onBackPressed()

        super.onBackPressed()
    }
}