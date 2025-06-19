package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.data.utils.ImageProcessor
import org.koin.dsl.module

val imageProcessorModule = module {
    single { ImageProcessor(get()) }
}