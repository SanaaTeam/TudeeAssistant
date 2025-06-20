package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.data.utils.PreferencesManagerImpl
import com.sanaa.tudee_assistant.domain.PreferencesManager
import org.koin.dsl.module

val preferencesManagerModule = module {
        single<PreferencesManager> { PreferencesManagerImpl(get()) }
}