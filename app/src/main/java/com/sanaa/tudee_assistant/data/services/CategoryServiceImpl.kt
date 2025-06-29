package com.sanaa.tudee_assistant.data.services

import com.sanaa.tudee_assistant.data.local.dao.CategoryDao
import com.sanaa.tudee_assistant.data.local.mapper.toDomain
import com.sanaa.tudee_assistant.data.local.mapper.toLocalDto
import com.sanaa.tudee_assistant.domain.entity.Category
import com.sanaa.tudee_assistant.domain.entity.CategoryCreationRequest
import com.sanaa.tudee_assistant.domain.exceptions.FailedToAddException
import com.sanaa.tudee_assistant.domain.exceptions.FailedToDeleteException
import com.sanaa.tudee_assistant.domain.exceptions.FailedToUpdateException
import com.sanaa.tudee_assistant.domain.exceptions.ModifyDefaultCategoryNotAllowedException
import com.sanaa.tudee_assistant.domain.exceptions.NotFoundException
import com.sanaa.tudee_assistant.domain.service.CategoryService
import kotlinx.coroutines.flow.Flow
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

    override suspend fun addCategory(categoryCreationRequest: CategoryCreationRequest) {
        if (categoryDao.insertCategory(categoryCreationRequest.toLocalDto()) == -1L) {
            throw FailedToAddException("Failed to add category")
        }
    }

    override suspend fun updateCategory(category: Category) {
        if (category.isDefault) {
            throw ModifyDefaultCategoryNotAllowedException("Update default category is not allowed")
        }
        if (categoryDao.updateCategory(category.toLocalDto()) <= 0) {
            throw FailedToUpdateException("Failed to update category")
        }
    }

    override suspend fun deleteCategoryById(categoryId: Int) {
        val category = categoryDao.getCategoryById(categoryId)
            ?: throw NotFoundException("Category not found")
        if (category.isDefault) {
            throw ModifyDefaultCategoryNotAllowedException("Deleting default category is not allowed")
        }
        if (categoryDao.deleteCategoryById(categoryId) <= 0) {
            throw FailedToDeleteException("Failed to delete category")
        }
    }
}