package com.app.sdkinit

import android.content.Context
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.AdjustEvent
import com.adjust.sdk.LogLevel

class AdjustManager {

    fun initializeAdjust(context: Context, appToken: String, isProduction: Boolean,deviceId :String?) {
        val config = AdjustConfig(context, appToken, if(isProduction) AdjustConfig.ENVIRONMENT_PRODUCTION else AdjustConfig.ENVIRONMENT_SANDBOX)
        config.setLogLevel(LogLevel.VERBOSE)
        if (deviceId != null){
            config.externalDeviceId = deviceId
        }
        Adjust.initSdk(config)

        ApplicationLifecycleCallback.register(context.applicationContext as android.app.Application)
    }

    fun sendEvent(eventToken: String) {
        val adjustEvent = AdjustEvent(eventToken)
        Adjust.trackEvent(adjustEvent)
    }

    fun sendRevenueEvent(eventToken: String, revenue: Double, currency: String) {
        val adjustEvent = AdjustEvent(eventToken)
        adjustEvent.setRevenue(revenue, currency)
        Adjust.trackEvent(adjustEvent)
    }
}