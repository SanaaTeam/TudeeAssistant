package com.sanaa.tudee_assistant.di


import android.content.Context
import androidx.room.Room
import com.sanaa.tudee_assistant.data.datasource.local.AppDatabase
import com.sanaa.tudee_assistant.data.datasource.local.dao.CategoryDao
import com.sanaa.tudee_assistant.data.datasource.local.dao.TaskDao
import org.koin.dsl.module

private const val DATABASE_NAME = "tudee.db"

internal fun provideDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DATABASE_NAME
    )
        .build()
}

internal fun provideTaskDao(db: AppDatabase): TaskDao = db.taskDao()
internal fun provideCategoryDao(db: AppDatabase): CategoryDao = db.categoryDao()

val databaseModule = module {
    single { provideDatabase(get()) }
    single { provideTaskDao(get()) }
    single { provideCategoryDao(get()) }
}

