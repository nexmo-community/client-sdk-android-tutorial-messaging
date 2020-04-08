package com.vonage.tutorial.messaging

import android.app.Application
import com.nexmo.client.NexmoClient

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Init the NexmoClient. You can retrieve NexmoClient instance latter by using NexmoClient.get()
        NexmoClient.Builder().build(this)
    }
}