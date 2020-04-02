package com.vonage.tutorial.messanging

import android.app.Application
import com.nexmo.client.NexmoClient

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Init NexmoClient. You can retrieve NexmoClient instance by using NexmoClient.get()
        NexmoClient.Builder().build(this)
    }
}