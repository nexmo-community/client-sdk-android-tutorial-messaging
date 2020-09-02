package com.vonage.tutorial.messaging;

import android.app.Application;
import com.nexmo.client.NexmoClient;

public final class BaseApplication extends Application {

    public void onCreate() {
        super.onCreate();

        this.initializeNexmoClient();
    }

    private final void initializeNexmoClient() {
        // Init the NexmoClient. You can retrieve NexmoClient instance latter by using NexmoClient.get()
        new NexmoClient.Builder().build(this);
    }
}