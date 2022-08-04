package com.timizatechnologies.crm

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.drawee.backends.pipeline.Fresco

class CRMApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
