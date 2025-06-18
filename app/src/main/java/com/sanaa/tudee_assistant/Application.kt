package com.sanaa.tudee_assistant

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TudeeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TudeeApplication)
            modules()
        }
    }
}
