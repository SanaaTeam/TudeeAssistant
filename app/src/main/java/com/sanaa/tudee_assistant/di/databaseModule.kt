package com.sanaa.tudee_assistant.di

import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sanaa.tudee_assistant.data.local.AppDatabase
import com.sanaa.tudee_assistant.data.utils.getDefaultCategories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val DATABASE_NAME = "tudee.db"

val databaseModule = module {
    single(named("databaseScope")) {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    single {
        Room.databaseBuilder(
            context = get(),
            AppDatabase::class.java,
            DATABASE_NAME
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                get<CoroutineScope>(named("databaseScope")).launch {
                    try {
                        val database = get<AppDatabase>()
                        getDefaultCategories().forEach { defaultCategory ->
                            database.categoryDao().insertCategory(defaultCategory)
                        }
                    } catch (e: Exception) {
                        Log.e("DB", "Failed to insert defaults", e)
                    }
                }
            }
        }).build()
    }
    single { get<AppDatabase>().taskDao() }
    single { get<AppDatabase>().categoryDao() }
}
