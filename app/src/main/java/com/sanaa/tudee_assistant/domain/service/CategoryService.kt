package com.sanaa.tudee_assistant.domain.service

import com.sanaa.tudee_assistant.domain.model.AddCategoryRequest
import com.sanaa.tudee_assistant.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryService {
    fun getCategories(): Flow<List<Category>>
    suspend fun getCategoryById(categoryId: Int): Category
    suspend fun addCategory(addCategoryRequest: AddCategoryRequest)
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategoryById(categoryId: Int)
}