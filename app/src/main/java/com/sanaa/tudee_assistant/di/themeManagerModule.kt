package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.data.utils.ThemeManager
import org.koin.dsl.module

val themeManagerModule = module {
        single { ThemeManager(get()) }
}