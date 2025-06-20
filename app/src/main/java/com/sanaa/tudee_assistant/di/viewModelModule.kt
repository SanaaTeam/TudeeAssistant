package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.presentation.screen.add_edit_screen.TaskFormViewModel
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.screen.category_task.CategoryTaskViewModel
import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreenViewModel
import com.sanaa.tudee_assistant.presentation.screen.category.CategoryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CategoryViewModel(get(), get(), get()) }
    viewModel {
        CategoryTaskViewModel(
            get<CategoryService>(), get<TaskService>()
        )
    }
    viewModel { HomeScreenViewModel(get(), get()) }
    viewModel { TaskFormViewModel(get(), get()) }
}