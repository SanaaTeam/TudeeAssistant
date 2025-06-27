package com.sanaa.tudee_assistant.presentation.mainActivity

import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val preferencesManager: PreferencesManager,
) : BaseViewModel<MainActivityUiState>(MainActivityUiState()) {
    init {
        loadScreen()
    }

    private fun loadScreen() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
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
    }

    fun onSetDarkTheme(isDarkTheme: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesManager.setDarkTheme(isDarkTheme)
        }
    }
}