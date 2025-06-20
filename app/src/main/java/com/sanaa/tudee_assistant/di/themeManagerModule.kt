package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.data.utils.ThemeManagerImpl
import com.sanaa.tudee_assistant.domain.ThemeManager
import org.koin.dsl.module

val themeManagerModule = module {
        single<ThemeManager> { ThemeManagerImpl(get()) }
}