package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.presentation.screen.taskScreen.TaskViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

viewModel { TaskViewModel(get()) }

}