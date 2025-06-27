package com.sanaa.tudee_assistant.data.services

import com.sanaa.tudee_assistant.data.local.dao.CategoryDao
import com.sanaa.tudee_assistant.data.local.mapper.toDomain
import com.sanaa.tudee_assistant.data.local.mapper.toLocalDto
import com.sanaa.tudee_assistant.domain.exceptions.DatabaseFailureException
import com.sanaa.tudee_assistant.domain.exceptions.DefaultCategoryException
import com.sanaa.tudee_assistant.domain.exceptions.FailedToAddException
import com.sanaa.tudee_assistant.domain.exceptions.FailedToDeleteException
import com.sanaa.tudee_assistant.domain.exceptions.FailedToUpdateException
import com.sanaa.tudee_assistant.domain.exceptions.NotFoundException
import com.sanaa.tudee_assistant.domain.model.AddCategoryRequest
import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.domain.service.CategoryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class CategoryServiceImpl(
    private val categoryDao: CategoryDao,
) : CategoryService {
    override suspend fun getCategoryById(categoryId: Int): Category {
        return categoryDao.getCategoryById(categoryId)?.toDomain()
            ?: throw NotFoundException("Category not found")
    }

    override fun getCategories(): Flow<List<Category>> =
        categoryDao.getAllCategories()
            .map { it.toDomain() }
            .catch {
                throw DatabaseFailureException(
                    message = "Failed to load categories from database duo to database error details :${it.message} ",
                    cause = it
                )
            }

    override suspend fun addCategory(addCategoryRequest: AddCategoryRequest) {
        if (categoryDao.insertCategory(addCategoryRequest.toLocalDto()) == -1L) {
            throw FailedToAddException("Failed to add category")
        }
    }

    override suspend fun updateCategory(category: Category) {
        if (categoryDao.updateCategory(category.toLocalDto()) <= 0) {
            throw FailedToUpdateException("Failed to update category")
        }
    }

    override suspend fun deleteCategoryById(categoryId: Int) {
        val category = categoryDao.getCategoryById(categoryId)
            ?: throw NotFoundException("Category not found")
        if (category.isDefault) {
            throw DefaultCategoryException("Deleting default category is not allowed")
        }
        if (categoryDao.deleteCategoryById(categoryId) <= 0) {
            throw FailedToDeleteException("Failed to delete category")
        }
    }
}