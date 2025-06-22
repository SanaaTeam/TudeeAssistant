package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.presentation.screen.add_edit_screen.TaskFormViewModel
import com.sanaa.tudee_assistant.presentation.screen.category.CategoryViewModel
import com.sanaa.tudee_assistant.presentation.screen.category_task.CategoryTaskViewModel
import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreenViewModel
import com.sanaa.tudee_assistant.presentation.screen.taskScreen.TaskViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CategoryViewModel(get(), get(), get()) }
    viewModel { CategoryTaskViewModel(get(), get()) }
    viewModel { HomeScreenViewModel(get(), get(), get()) }
    viewModel { TaskViewModel(get(), get()) }
    viewModel { TaskFormViewModel(get(), get()) }
}