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
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel(get(), dispatcher = Dispatchers.IO) }
    viewModel { OnBoardingViewModel(get(), dispatcher = Dispatchers.IO) }
    viewModel { HomeScreenViewModel(get(), get(), get(), get(), dispatcher = Dispatchers.IO) }
        viewModel { CategoryViewModel(get(), get(), get(), get(), dispatcher = Dispatchers.IO) }
    viewModel {
        CategoryTaskViewModel(
            get(), get(), get(), get(), get(), dispatcher = Dispatchers.IO
        )
    }
    viewModel { TaskViewModel(get(), get(), get(), get(), get(), dispatcher = Dispatchers.IO) }
    viewModel { AddEditTaskViewModel(get(), get(), dispatcher = Dispatchers.IO) }
    viewModel { TaskViewModel(get(), get(), get(), get(), get(), dispatcher = Dispatchers.IO) }
    viewModel { TaskDetailsBottomSheetViewModel(get(), get(), get(), dispatcher = Dispatchers.IO) }

}