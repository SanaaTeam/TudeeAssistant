package com.sanaa.tudee_assistant.presentation.app

import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.Dispatchers
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
            preferencesManager.isDarkTheme.collect { isDarkTheme ->
                _state.update { it.copy(isDarkTheme = isDarkTheme) }
            }

            preferencesManager.isFirstLaunch.collect { isFirstLaunch ->
                _state.update { it.copy(isFirstLaunch = isFirstLaunch) }
            }
        }
    }
}