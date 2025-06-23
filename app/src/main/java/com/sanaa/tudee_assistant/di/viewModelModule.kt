package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.presentation.screen.tasks.addEditTask.TaskFormViewModel
import com.sanaa.tudee_assistant.presentation.screen.category.CategoryViewModel
import com.sanaa.tudee_assistant.presentation.screen.categoryTask.CategoryTaskViewModel
import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreenViewModel
import com.sanaa.tudee_assistant.presentation.screen.onBoarding.OnBoardingViewModel
import com.sanaa.tudee_assistant.presentation.screen.tasks.TaskViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::OnBoardingViewModel)
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::CategoryViewModel)
    viewModelOf(::CategoryTaskViewModel)
    viewModelOf(::TaskViewModel)
    viewModelOf(::TaskFormViewModel)
}