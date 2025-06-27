package com.sanaa.tudee_assistant.presentation.mainActivity

import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class MainActivityViewModel(
    private val preferencesManager: PreferencesManager,
) : BaseViewModel<MainActivityUiState>(MainActivityUiState()) {
    init {
        initialValues()
        loadScreen()
    }

    private fun initialValues() {
        tryToExecute(
            callee = {
                preferencesManager.changeTaskStatus(Task.TaskStatus.IN_PROGRESS)
            }
        )
    }

    private fun loadScreen() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            callee = {
                preferencesManager.isDarkTheme.combine(preferencesManager.isFirstLaunch) { isDarkTheme, isFirstLaunch ->
                    Pair(isDarkTheme, isFirstLaunch)
                }.collect { (isDarkTheme, isFirstLaunch) ->

                    _state.update {
                        it.copy(
                            isDarkTheme = isDarkTheme,
                            isFirstLaunch = isFirstLaunch,
                            isLoading = false
                        )
                    }
                }
            }
        )
    }

    fun onSetDarkTheme(isDarkTheme: Boolean) {
        tryToExecute(
            callee = {
                preferencesManager.setDarkTheme(isDarkTheme)
            }
        )
    }
}