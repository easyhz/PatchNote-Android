package com.easyhz.patchnote.ui.navigation.defect

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.easyhz.patchnote.core.designSystem.util.transition.SlideDirection
import com.easyhz.patchnote.core.designSystem.util.transition.enterSlide
import com.easyhz.patchnote.core.designSystem.util.transition.exitSlide
import com.easyhz.patchnote.ui.navigation.home.navigateToHome
import com.easyhz.patchnote.ui.screen.defectDetail.DefectDetailScreen
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
            navigateToHome = { navController.navigateToHome(navOptions = navOptions) }
        )
    }

    composable<DefectDetail> {
        val args = it.toRoute<DefectDetail>()
        DefectDetailScreen(
            defectId = args.defectId,
            navigateToUp = navController::navigateUp
        )
    }
}

fun NavController.navigateToDefectEntry() {
    navigate(DefectEntry)
}

fun NavController.navigateToDefectDetail(defectId: String) {
    navigate(DefectDetail(defectId = defectId))
}