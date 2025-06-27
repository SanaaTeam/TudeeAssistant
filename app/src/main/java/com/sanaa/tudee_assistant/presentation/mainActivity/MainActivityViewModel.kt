package com.sanaa.tudee_assistant.presentation.mainActivity

import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val preferencesManager: PreferencesManager,
) : BaseViewModel<MainActivityUiState>(MainActivityUiState()) {
    init {
        initialValues()
        loadScreen()
    }

    private fun initialValues() {
        viewModelScope.launch {
            preferencesManager.changeTaskStatus(Task.TaskStatus.IN_PROGRESS)
        }
    }

    private fun loadScreen() {
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            preferencesManager.isDarkTheme.combine(preferencesManager.isFirstLaunch) { isDarkTheme, isFirstLaunch ->
                Pair(isDarkTheme, isFirstLaunch)
            }.collect { (isDarkTheme, isFirstLaunch) ->
                updateState {
                    it.copy(
                        isDarkTheme = isDarkTheme,
                        isFirstLaunch = isFirstLaunch,
                        isLoading = false
                    )
                }
            }
        }
    }
}