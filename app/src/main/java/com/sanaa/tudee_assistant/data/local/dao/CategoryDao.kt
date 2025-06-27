package com.sanaa.tudee_assistant.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sanaa.tudee_assistant.data.local.dto.CategoryLocalDto
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryLocalDto): Long

    @Update
    suspend fun updateCategory(category: CategoryLocalDto): Int

    @Query("DELETE FROM categories WHERE category_id = :categoryId")
    suspend fun deleteCategoryById(categoryId: Int): Int

    @Query("DELETE FROM categories WHERE is_default = 0")
    suspend fun deleteAllCategory(): Int

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<CategoryLocalDto>>

    @Query("SELECT * FROM categories WHERE category_id = :categoryId")
    suspend fun getCategoryById(categoryId: Int): CategoryLocalDto?
}