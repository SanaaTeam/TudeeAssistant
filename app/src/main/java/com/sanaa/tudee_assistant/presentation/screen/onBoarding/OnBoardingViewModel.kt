package com.sanaa.tudee_assistant.presentation.screen.onBoarding

import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.presentation.model.OnBoardingPageContentItem
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val preferencesManager: PreferencesManager
) : BaseViewModel<OnBoardingScreenUiState>(OnBoardingScreenUiState()),
    OnBoardingScreenInteractionListener {

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    pageList = getOnBoardingPageContent(),
                    currentPageIndex = 0
                )
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

    private fun getOnBoardingPageContent(): List<OnBoardingPageContentItem> {
        return listOf(
            OnBoardingPageContentItem(
                title = R.string.onboarding_title_0,
                description = R.string.onboarding_desc_0,
                imageRes = R.drawable.robot_onboarding_page0
            ),
            OnBoardingPageContentItem(
                title = R.string.onboarding_title_1,
                description = R.string.onboarding_desc_1,
                imageRes = R.drawable.robot_onboarding_page1
            ),
            OnBoardingPageContentItem(
                title = R.string.onboarding_title_2,
                description = R.string.onboarding_desc_2,
                imageRes = R.drawable.robot_onboarding_page2
            ),
        )
    }
}