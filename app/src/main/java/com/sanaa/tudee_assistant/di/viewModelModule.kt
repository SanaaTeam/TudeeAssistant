package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.screen.category_task.CategoryTaskViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        CategoryTaskViewModel(
            get<CategoryService>(), get<TaskService>()
        )
    }

}