package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.data.utils.ThemeManager
import org.koin.dsl.module

val themManagerModule = module {
        single { ThemeManager(get()) }
}