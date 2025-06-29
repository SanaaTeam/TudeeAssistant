package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.data.services.CategoryServiceImpl
import com.sanaa.tudee_assistant.data.services.ImageProcessorImpl
import com.sanaa.tudee_assistant.data.services.PreferencesManagerImpl
import com.sanaa.tudee_assistant.data.services.TaskServiceImpl
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.ImageProcessor
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.domain.service.StringProvider
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.utils.StringProviderImpl
import org.koin.dsl.module

val servicesModule = module {
    single<ImageProcessor> { ImageProcessorImpl(get()) }
    single<PreferencesManager> { PreferencesManagerImpl(get()) }

    single<TaskService> { TaskServiceImpl(get()) }
    single<CategoryService> { CategoryServiceImpl(get()) }
    single<StringProvider> { StringProviderImpl(context = get()) }
}