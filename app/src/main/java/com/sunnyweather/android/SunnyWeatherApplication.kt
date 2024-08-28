package com.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * author: YangKui
 * data: 2023/10/26 08:08
 * des:
 */
class SunnyWeatherApplication : Application() {
    companion object {
        const val TOKEN = "IcLHrkgsrPnGf7PY"
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}