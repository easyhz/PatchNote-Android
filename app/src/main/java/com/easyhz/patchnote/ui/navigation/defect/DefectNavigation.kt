package com.easyhz.patchnote.ui.navigation.defect

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easyhz.patchnote.core.designSystem.util.transition.SlideDirection
import com.easyhz.patchnote.core.designSystem.util.transition.enterSlide
import com.easyhz.patchnote.core.designSystem.util.transition.exitSlide
import com.easyhz.patchnote.ui.screen.defectEntry.DefectEntryScreen

internal fun NavGraphBuilder.defectGraph(
    navController: NavController
) {
    composable<DefectEntry>(
        enterTransition = { enterSlide(SlideDirection.Up) },
        exitTransition = { exitSlide(SlideDirection.Up) },
        popEnterTransition = { enterSlide(SlideDirection.Down) },
        popExitTransition = { exitSlide(SlideDirection.Down) }
    ) {
        DefectEntryScreen(
            navigateToUp = navController::navigateUp
        )
    }
}

fun NavController.navigateToDefectEntry() {
    navigate(DefectEntry)
}