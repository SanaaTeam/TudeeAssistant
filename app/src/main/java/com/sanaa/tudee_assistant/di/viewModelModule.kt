package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.taskDetailsBottomSheet.TaskDetailsBottomSheetViewModel
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.screen.tasks.addEditTask.TaskFormViewModel
import com.sanaa.tudee_assistant.presentation.screen.category.CategoryViewModel
import com.sanaa.tudee_assistant.presentation.screen.categoryTask.CategoryTaskViewModel
import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreenViewModel
import com.sanaa.tudee_assistant.presentation.screen.tasks.TaskViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::CategoryViewModel)
    viewModelOf(::CategoryTaskViewModel)
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::TaskFormViewModel)
    viewModel{(selectedStatusTab:TaskUiStatus)-> TaskViewModel(taskService = get(), categoryService = get(),selectedStatusTab = selectedStatusTab) }
    viewModel{(selectedTaskId:Int)-> TaskDetailsBottomSheetViewModel(taskService = get(), categoryService = get(),selectedTaskId = selectedTaskId)}



}