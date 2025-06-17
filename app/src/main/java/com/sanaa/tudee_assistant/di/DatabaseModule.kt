package com.sanaa.tudee_assistant.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sanaa.tudee_assistant.data.local.AppDatabase
import com.sanaa.tudee_assistant.data.utils.getDefaultCategories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.dsl.module

private const val DATABASE_NAME = "tudee.db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        val database = get<AppDatabase>()
                        getDefaultCategories().forEach {defaultCategory->
                            database.categoryDao().insertCategory(defaultCategory)
                        }

                    }
                }
            })
            .build()
    }
    single { get<AppDatabase>().taskDao() }
    single { get<AppDatabase>().categoryDao() }

}
