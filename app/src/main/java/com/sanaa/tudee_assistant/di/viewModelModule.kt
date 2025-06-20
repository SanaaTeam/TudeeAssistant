package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.presentation.bottom_sheet.add_edit_task.TaskFormViewModel
import com.sanaa.tudee_assistant.presentation.screen.category.CategoryViewModel
import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreenViewModel
import com.sanaa.tudee_assistant.presentation.screen.task.TaskViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TaskViewModel(get(), get()) }
    viewModel { TaskFormViewModel(get(), get()) }
    viewModel { HomeScreenViewModel(get(), get()) }
    viewModel { CategoryViewModel(get(), get(), get()) }
}