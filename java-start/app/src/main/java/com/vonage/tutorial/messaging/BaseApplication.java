package com.vonage.tutorial.messaging;

import android.app.Application;
import com.vonage.tutorial.messaging.util.Tutorial;

public final class BaseApplication extends Application {

    public void onCreate() {
        super.onCreate();

        this.initializeNexmoClient();
    }

    private final void initializeNexmoClient() {
        // Init the NexmoClient. You can retrieve NexmoClient instance latter by using NexmoClient.get()
        Tutorial.todo("Init NexmoClient here");
    }
}