package com.sanaa.tudee_assistant.di


import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sanaa.tudee_assistant.data.local.AppDatabase
import com.sanaa.tudee_assistant.data.local.dto.CategoryLocalDto
import com.sanaa.tudee_assistant.data.utils.getDefaultCategories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.compose.getKoin
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














//
//internal fun provideDatabase(context: Context): AppDatabase {
//    return Room.databaseBuilder(
//        context,
//        AppDatabase::class.java,
//        DATABASE_NAME
//    )
//        .build()
//}
//
//internal fun provideTaskDao(db: AppDatabase): TaskDao = db.taskDao()
//internal fun provideCategoryDao(db: AppDatabase): CategoryDao = db.categoryDao()
//
//val databaseModule = module {
//    single { provideDatabase(get()) }
//    single { provideTaskDao(get()) }
//    single { provideCategoryDao(get()) }
//}





//
//class DatabaseCallback(
//    private val context: Context
//) : RoomDatabase.Callback() {
//
//    override fun onCreate(db: SupportSQLiteDatabase) {
//        super.onCreate(db)
//        CoroutineScope(Dispatchers.IO).launch {
//            val dao = AppDatabase.getDatabase(context).categoryDao()
//            dao.insertAll(getDefaultCategories())
//        }
//    }
