package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.data.utils.ImageProcessorImpl
import com.sanaa.tudee_assistant.domain.ImageProcessor
import org.koin.dsl.module

val imageProcessorModule = module {
    single<ImageProcessor> { ImageProcessorImpl(get()) }
}