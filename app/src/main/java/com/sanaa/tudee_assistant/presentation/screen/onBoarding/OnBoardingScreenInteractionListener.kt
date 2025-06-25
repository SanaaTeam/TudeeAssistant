package com.sanaa.tudee_assistant.presentation.screen.onBoarding

import androidx.navigation.NavHostController

interface OnBoardingScreenInteractionListener {
    fun onNextPageClick(navHostController: NavHostController)
    fun onSkipClick(navHostController: NavHostController,)
    fun setCurrentPage(pageIndex: Int)
}