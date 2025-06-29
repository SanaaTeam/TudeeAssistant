package com.sanaa.tudee_assistant.presentation.mainActivity

import com.sanaa.tudee_assistant.domain.entity.Task
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.presentation.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine

class MainActivityViewModel(
    private val preferencesManager: PreferencesManager,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel<MainActivityUiState>(MainActivityUiState(), dispatcher) {
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
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            callee = {
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