package com.sanaa.tudee_assistant.presentation.screen.onBoarding

import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import com.sanaa.tudee_assistant.presentation.utils.DataProvider
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val preferencesManager: PreferencesManager
) : BaseViewModel<OnBoardingScreenUiState>(OnBoardingScreenUiState()),
    OnBoardingScreenInteractionListener {

    init {
        viewModelScope.launch {
            preferencesManager.isDarkTheme.collect { isDarkTheme ->
                _state.update {
                    it.copy(
                        isDarkTheme = isDarkTheme,
                        pageList = DataProvider.getOnBoardingPageContent(),
                        currentPageIndex = 0
                    )
                }
            }
        }
    }

    override fun onNextPageClick() {
        if (state.value.currentPageIndex == state.value.pageList.lastIndex) {
            onSkipClick()
        } else {
            _state.update { it.copy(currentPageIndex = it.currentPageIndex + 1) }
        }
    }

    override fun onSkipClick() {
            _state.update { it.copy(isSkipable = true) }

        viewModelScope.launch {
            preferencesManager.setOnboardingCompleted()
        }
    }

    override fun setCurrentPage(pageIndex: Int) {
        _state.update { it.copy(currentPageIndex = pageIndex) }
    }
}