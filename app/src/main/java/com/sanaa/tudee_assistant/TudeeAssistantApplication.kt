package com.sanaa.tudee_assistant

import android.app.Application
import com.sanaa.tudee_assistant.data.utils.ThemeManager
import com.sanaa.tudee_assistant.di.databaseModule
import com.sanaa.tudee_assistant.di.imageProcessorModule
import com.sanaa.tudee_assistant.di.servicesModule
import com.sanaa.tudee_assistant.di.themManagerModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TudeeAssistantApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TudeeAssistantApplication)
            modules(
                databaseModule,
                servicesModule,
                imageProcessorModule,
                themManagerModule
            )
        }
    }

}