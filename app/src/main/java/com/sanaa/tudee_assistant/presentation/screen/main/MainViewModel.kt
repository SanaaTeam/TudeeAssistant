package com.sanaa.tudee_assistant.presentation.screen.main

import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.update


class MainViewModel(
    private val preferencesManager: PreferencesManager,
) : BaseViewModel<MainUiState>(MainUiState()) {
    init {
        loadScreen()
    }

    private fun loadScreen() {
        tryToExecute(
            callee = {
                preferencesManager.lastSelectedTaskStatus.collect { lastSelectedTaskStatus ->
                    _state.update { it.copy(lastSelectedTaskStatus = lastSelectedTaskStatus) }
                }
            },
            onError = ::showErrorMessage
        )
    }

    fun showErrorMessage(exception: Exception) {

    }
}