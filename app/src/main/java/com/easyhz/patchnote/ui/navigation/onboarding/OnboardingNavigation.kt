package com.easyhz.patchnote.ui.navigation.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easyhz.patchnote.ui.screen.onboarding.OnboardingScreen

fun NavGraphBuilder.onboardingNavigation(
    navController: NavController,
) {
    composable<Onboarding> {
        OnboardingScreen {

        }
    }
}