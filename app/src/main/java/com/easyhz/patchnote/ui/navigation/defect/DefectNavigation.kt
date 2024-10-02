package com.easyhz.patchnote.ui.navigation.defect

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.easyhz.patchnote.core.designSystem.util.transition.SlideDirection
import com.easyhz.patchnote.core.designSystem.util.transition.enterSlide
import com.easyhz.patchnote.core.designSystem.util.transition.exitSlide
import com.easyhz.patchnote.ui.navigation.home.navigateToHome
import com.easyhz.patchnote.ui.screen.defectEntry.DefectEntryScreen

internal fun NavGraphBuilder.defectGraph(
    navController: NavController
) {
    composable<DefectEntry>(
        enterTransition = { enterSlide(SlideDirection.Up) },
        exitTransition = { exitSlide(SlideDirection.Down) },
        popEnterTransition = { enterSlide(SlideDirection.Up) },
        popExitTransition = { exitSlide(SlideDirection.Down) }
    ) {
        val navOptions = navOptions {
            popUpTo(navController.graph.id) { inclusive = true }
        }
        DefectEntryScreen(
            navigateToUp = navController::navigateUp,
            navigateToHome = { navController.navigateToHome(navOptions) }
        )
    }
}

fun NavController.navigateToDefectEntry() {
    navigate(DefectEntry)
}