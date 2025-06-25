package com.sanaa.tudee_assistant.presentation.app

import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TudeeAppViewModel(
    private val preferencesManager: PreferencesManager,
) : BaseViewModel<TudeeAppUiState>(TudeeAppUiState()) {
    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesManager.isDarkTheme.combine(preferencesManager.isFirstLaunch) { isDarkTheme, isFirstLaunch ->
                Pair(isDarkTheme, isFirstLaunch)
            }.collect { (isDarkTheme, isFirstLaunch) ->
                _state.update {
                    it.copy(
                        isDarkTheme = isDarkTheme,
                        isFirstLaunch = isFirstLaunch
                    )
                }
            }
        }
    }
}