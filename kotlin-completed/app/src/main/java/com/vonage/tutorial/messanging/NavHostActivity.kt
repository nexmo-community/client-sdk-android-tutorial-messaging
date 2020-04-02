package com.vonage.tutorial.messanging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.vonage.tutorial.R
import kotlinx.android.synthetic.main.activity_nav_host.*

class NavHostActivity : AppCompatActivity(R.layout.activity_nav_host) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val host = navHostFragment as NavHostFragment
        val navController = host.navController

        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp() =
        (navHostFragment as NavHostFragment).navController.navigateUp()
}
