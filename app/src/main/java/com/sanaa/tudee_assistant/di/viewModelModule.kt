package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreenViewModel
import com.sanaa.tudee_assistant.presentation.screens.category.CategoryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CategoryViewModel(get(), get(), get()) }
    viewModel { HomeScreenViewModel(get(), get()) }
}