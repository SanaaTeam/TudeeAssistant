package com.sanaa.tudee_assistant.di

import com.sanaa.tudee_assistant.core.util.ImageProcessor
import org.koin.dsl.module

val imageProcessorModule = module {
    single { ImageProcessor(get()) }
}