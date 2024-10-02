package com.easyhz.patchnote.ui.navigation.home

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easyhz.patchnote.core.designSystem.util.transition.SlideDirection
import com.easyhz.patchnote.core.designSystem.util.transition.enterSlide
import com.easyhz.patchnote.core.designSystem.util.transition.exitSlide
import com.easyhz.patchnote.ui.navigation.dataManagement.navigateToDataManagement
import com.easyhz.patchnote.ui.navigation.defect.navigateToDefectEntry
import com.easyhz.patchnote.ui.screen.home.HomeScreen

internal fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    composable<Home>(
        enterTransition = { enterSlide(SlideDirection.Start) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { exitSlide(SlideDirection.End) }
    ) {
        HomeScreen(
            navigateToDataManagement = navController::navigateToDataManagement,
            navigateToDefectEntry = navController::navigateToDefectEntry
        )
    }
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(Home, navOptions)
}