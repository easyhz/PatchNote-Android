package com.easyhz.patchnote.ui.navigation.dataManagement

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easyhz.patchnote.core.designSystem.util.transition.SlideDirection
import com.easyhz.patchnote.core.designSystem.util.transition.enterSlide
import com.easyhz.patchnote.core.designSystem.util.transition.exitSlide
import com.easyhz.patchnote.ui.screen.dataEntry.DataEntryScreen
import com.easyhz.patchnote.ui.screen.dataManagement.DataManagementScreen

internal fun NavGraphBuilder.dataManagementGraph(
    navController: NavController
) {
    composable<DataManagement>(
        enterTransition = { enterSlide(SlideDirection.Start) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { exitSlide(SlideDirection.End) }
    ) {
        DataManagementScreen(
            navigateToDataEntry = navController::navigateToDataEntry,
            navigateToUp = navController::navigateUp
        )
    }

    composable<DataEntry>(
        enterTransition = { enterSlide(SlideDirection.Up) },
        exitTransition = { exitSlide(SlideDirection.Up) },
        popEnterTransition = { enterSlide(SlideDirection.Down) },
        popExitTransition = { exitSlide(SlideDirection.Down) }
    ) {
        DataEntryScreen(
            navigateToUp = navController::navigateUp
        )
    }
}

fun NavController.navigateToDataManagement() {
    navigate(DataManagement)
}

fun NavController.navigateToDataEntry() {
    navigate(DataEntry)
}