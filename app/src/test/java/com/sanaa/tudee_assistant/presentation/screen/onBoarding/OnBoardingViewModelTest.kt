package com.sanaa.tudee_assistant.presentation.screen.onBoarding

import com.google.common.truth.Truth.assertThat
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnBoardingViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: OnBoardingViewModel

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var isDarkThemeFlow: MutableStateFlow<Boolean>

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        preferencesManager = mockk(relaxed = true)
        viewModel = OnBoardingViewModel(preferencesManager)

        isDarkThemeFlow = MutableStateFlow(false)
        every { preferencesManager.isDarkTheme } returns isDarkThemeFlow
    }

    @Test
    fun `init should update the pageList`() = runTest {

        assertThat(viewModel.state.value.pageList).isNotEmpty()
    }

    @Test
    fun `onNextPageClick should increment currentPageIndex when not last page`() = runTest {
        val initialIndex = viewModel.state.value.currentPageIndex

        viewModel.onNextPageClick()

        assertThat(viewModel.state.value.currentPageIndex).isEqualTo(initialIndex + 1)
    }

    @Test
    fun `onNextPageClick should call onSkipClick at last page`() = runTest {
        val lastIndex = viewModel.state.value.pageList.lastIndex
        viewModel.setCurrentPage(lastIndex)

        viewModel.onNextPageClick()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.isSkipable).isTrue()
    }

    @Test
    fun `onSkipClick should set isSkipable true`() = runTest {

        viewModel.onSkipClick()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.isSkipable).isTrue()
    }

    @Test
    fun `onSkipClick should call preferencesManager setOnboardingCompleted`() = runTest {

        viewModel.onSkipClick()
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) { preferencesManager.setOnboardingCompleted() }
    }

    @Test
    fun `setCurrentPage should update currentPageIndex`() = runTest {

        viewModel.setCurrentPage(2)

        assertThat(viewModel.state.value.currentPageIndex).isEqualTo(2)
    }
}