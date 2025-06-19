package com.sanaa.tudee_assistant.presentation.screens.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.CategoryService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel() : ViewModel() {

    private val _state = MutableStateFlow(CategoryUiState())
    val state: StateFlow<CategoryUiState>
        get() = _state

    init {
        loadCategoriesWithTasks()
    }


    private fun loadCategoriesWithTasks() {
        viewModelScope.launch {
            try {
                TODO()

                _state.update {
                    it.copy(

                    )
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    fun getTaskCount(categoryId: Int): Int {
        return TODO()
    }


}
