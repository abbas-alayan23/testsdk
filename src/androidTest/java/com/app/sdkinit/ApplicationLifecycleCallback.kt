package com.app.sdkinit
import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.adjust.sdk.Adjust

object ApplicationLifecycleCallback : Application.ActivityLifecycleCallbacks {
    fun register(application: Application) {
        application.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityResumed(activity: Activity) {
        Adjust.onResume()
    }

    override fun onActivityPaused(activity: Activity) {
        Adjust.onPause()
    }

    // You can override other lifecycle methods as necessary
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
}