package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.data.services.TasksServiceImpl
import com.sanaa.tudee_assistant.domain.service.TasksServices
import org.koin.dsl.module

val servicesModule = module {
    single<TasksServices> { TasksServiceImpl(get()) }
}