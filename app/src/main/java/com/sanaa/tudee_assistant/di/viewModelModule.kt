package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.presentation.TudeeAppViewModel
import com.sanaa.tudee_assistant.presentation.screen.category.CategoryViewModel
import com.sanaa.tudee_assistant.presentation.screen.categoryTask.CategoryTaskViewModel
import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreenViewModel
import com.sanaa.tudee_assistant.presentation.screen.tasks.TaskViewModel
import com.sanaa.tudee_assistant.presentation.screen.tasks.addEditTask.TaskFormViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::TudeeAppViewModel)
    viewModelOf(::CategoryViewModel)
    viewModelOf(::CategoryTaskViewModel)
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::TaskViewModel)
    viewModelOf(::TaskFormViewModel)
}