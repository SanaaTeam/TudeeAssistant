package com.sanaa.tudee_assistant

import android.app.Application
import com.sanaa.tudee_assistant.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()

        // start koin dependency injection
        startKoin {
            androidContext(this@MyApplication)
            modules(
                databaseModule,
            )
        }
    }

}