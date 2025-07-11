package com.sanaa.tudee_assistant.presentation.screen.main

import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.presentation.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


class MainViewModel(
    private val preferencesManager: PreferencesManager,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel<MainUiState>(MainUiState(), dispatcher) {
    init {
        loadScreen()
    }

    private fun loadScreen() {
        tryToExecute(
            callee = {
                preferencesManager.lastSelectedTaskStatus.collect { lastSelectedTaskStatus ->
                    updateState { it.copy(lastSelectedTaskStatus = lastSelectedTaskStatus) }
                }
            }
        )
    }
}