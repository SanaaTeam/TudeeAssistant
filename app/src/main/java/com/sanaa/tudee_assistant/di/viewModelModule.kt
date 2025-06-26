package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.taskDetailsBottomSheet.TaskDetailsBottomSheetViewModel
import com.sanaa.tudee_assistant.presentation.mainActivity.MainActivityViewModel
import com.sanaa.tudee_assistant.presentation.screen.category.CategoryViewModel
import com.sanaa.tudee_assistant.presentation.screen.categoryTask.CategoryTaskViewModel
import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreenViewModel
import com.sanaa.tudee_assistant.presentation.screen.onBoarding.OnBoardingViewModel
import com.sanaa.tudee_assistant.presentation.screen.tasks.TaskViewModel
import com.sanaa.tudee_assistant.presentation.screen.tasks.addEditTask.AddEditTaskViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainActivityViewModel)
    viewModelOf(::OnBoardingViewModel)
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::CategoryViewModel)
    viewModelOf(::CategoryTaskViewModel)
    viewModelOf(::TaskViewModel)
    viewModelOf(::AddEditTaskViewModel)
    viewModelOf(::TaskViewModel)
    viewModel {
        TaskDetailsBottomSheetViewModel(
            taskService = get(),
            categoryService = get(),
            stringProvider = get(),
            dispatcher = Dispatchers.IO
        )
    }

}