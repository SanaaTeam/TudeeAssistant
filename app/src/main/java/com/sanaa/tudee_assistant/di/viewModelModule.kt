package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeScreenViewModel(get(), get()) }
}