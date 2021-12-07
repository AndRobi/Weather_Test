package com.fb.weathertest

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherTestApplication : Application() {
    init {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build()
            )
        }
    }
}
