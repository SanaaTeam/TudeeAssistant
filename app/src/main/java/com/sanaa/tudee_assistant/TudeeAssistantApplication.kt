package com.sanaa.tudee_assistant

import android.app.Application
import com.sanaa.tudee_assistant.di.databaseModule
import com.sanaa.tudee_assistant.di.imageProcessorModule
import com.sanaa.tudee_assistant.di.servicesModule
import com.sanaa.tudee_assistant.di.themeManagerModule
import com.sanaa.tudee_assistant.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TudeeAssistantApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TudeeAssistantApplication)
            modules(
                viewModelModule,
                databaseModule,
                servicesModule,
                imageProcessorModule,
                themeManagerModule,
            )
        }
    }

}