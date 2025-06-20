package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.presentation.screen.add_edit_screen.TaskFormViewModel
import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreenViewModel
import com.sanaa.tudee_assistant.presentation.screen.taskScreen.TaskViewModel
import com.sanaa.tudee_assistant.presentation.screens.category.CategoryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { TaskFormViewModel(get(), get()) }
    viewModel { TaskViewModel(get(), get()) }
    viewModel { CategoryViewModel(get(), get(), get()) }
    viewModel { HomeScreenViewModel(get(), get()) }
}