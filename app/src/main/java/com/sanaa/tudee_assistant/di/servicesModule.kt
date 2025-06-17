package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.data.services.CategoryServiceImpl
import com.sanaa.tudee_assistant.data.services.TasksServiceImpl
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import org.koin.dsl.module

val servicesModule = module {
    single<TaskService> { TasksServiceImpl(get()) }
    single<CategoryService> { CategoryServiceImpl(get()) }
}