package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.data.services.CategoryServiceImpl
import com.sanaa.tudee_assistant.data.services.TasksServiceImpl
import com.sanaa.tudee_assistant.domain.service.CategoriesServices
import com.sanaa.tudee_assistant.domain.service.TasksServices
import org.koin.dsl.module

val servicesModule = module {
    single<TasksServices> { TasksServiceImpl(get()) }
    single<CategoriesServices> { CategoryServiceImpl(get()) }
}