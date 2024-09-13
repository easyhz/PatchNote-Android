package com.easyhz.patchnote.ui.navigation.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easyhz.patchnote.ui.navigation.sign.navigateToSign
import com.easyhz.patchnote.ui.screen.onboarding.OnboardingScreen

fun NavGraphBuilder.onboardingGraph(
    navController: NavController,
) {
    composable<Onboarding> {
        OnboardingScreen(
            navigateToSign = navController::navigateToSign
        )
    }
}