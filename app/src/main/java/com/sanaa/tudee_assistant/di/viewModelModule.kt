package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.data.utils.ImageProcessor
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.screens.category.CategoryViewModel
import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        CategoryViewModel(
            get<CategoryService>(), get<TaskService>(), get<ImageProcessor>()
        )
    }
    viewModel { HomeScreenViewModel(get(), get()) }
}
