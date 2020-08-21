package com.vonage.tutorial.messaging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.vonage.tutorial.R
import com.vonage.tutorial.messaging.extension.currentNavigationFragment
import com.vonage.tutorial.messaging.util.NavManager
import kotlinx.android.synthetic.main.activity_nav_host.*

class NavHostActivity : AppCompatActivity(R.layout.activity_nav_host) {

    private val navManager = NavManager
    private val navController get() = navHostFragment.findNavController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navManager.init(navController)
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
