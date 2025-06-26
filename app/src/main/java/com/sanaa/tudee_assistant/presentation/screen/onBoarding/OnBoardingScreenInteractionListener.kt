package com.sanaa.tudee_assistant.presentation.screen.onBoarding

interface OnBoardingScreenInteractionListener {
    fun onNextPageClick()
    fun onSkipClick()
    fun setCurrentPage(pageIndex: Int)
}