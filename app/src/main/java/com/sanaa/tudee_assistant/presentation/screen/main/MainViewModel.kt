package com.sanaa.tudee_assistant.presentation.screen.main

import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MainViewModel(
    private val preferencesManager: PreferencesManager,
) : BaseViewModel<MainUiState>(MainUiState()) {
    init {
        loadScreen()
    }

    private fun loadScreen() {
        viewModelScope.launch {
            preferencesManager.lastSelectedTaskStatus.collect { lastSelectedTaskStatus ->
                _state.update { it.copy(lastSelectedTaskStatus = lastSelectedTaskStatus) }
            }
        }
    }
}