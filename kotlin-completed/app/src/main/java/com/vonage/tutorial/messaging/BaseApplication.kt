package com.vonage.tutorial.messaging

import android.app.Application
import com.nexmo.client.NexmoClient

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initializeNexmoClient()
    }

    private fun initializeNexmoClient() {
        // Init the NexmoClient. You can retrieve NexmoClient instance latter by using NexmoClient.get()
        NexmoClient.Builder().build(this)
    }
}