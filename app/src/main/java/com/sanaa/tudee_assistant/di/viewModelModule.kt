package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.domain.ImageProcessor
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.screen.category_task.CategoryTaskViewModel
import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreenViewModel
import com.sanaa.tudee_assistant.presentation.screens.category.CategoryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        CategoryViewModel(
            get<CategoryService>(), get<TaskService>(), get<ImageProcessor>()
        )
    }
    viewModel {
        CategoryTaskViewModel(
            get<CategoryService>(), get<TaskService>()
        )
    }
    viewModel { HomeScreenViewModel(get(), get()) }
}
