package com.easyhz.patchnote.ui.navigation.defect

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.easyhz.patchnote.core.designSystem.util.transition.SlideDirection
import com.easyhz.patchnote.core.designSystem.util.transition.enterSlide
import com.easyhz.patchnote.core.designSystem.util.transition.exitSlide
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.DefectMainItem
import com.easyhz.patchnote.ui.navigation.home.Home
import com.easyhz.patchnote.ui.navigation.home.navigateToHome
import com.easyhz.patchnote.ui.screen.defect.defectCompletion.DefectCompletionScreen
import com.easyhz.patchnote.ui.screen.defect.defectDetail.DefectDetailScreen
import com.easyhz.patchnote.ui.screen.defect.defectEntry.DefectEntryScreen

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

    composable<DefectDetail>(
        typeMap = DefectDetail.typeMap,
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { exitSlide(SlideDirection.End) }
    ) {
        DefectDetailScreen(
            navigateToUp = navController::navigateUp,
            navigateToDefectCompletion = navController::navigateToDefectCompletion
        )
    }

    composable<DefectCompletion>(
        typeMap = DefectCompletion.typeMap,
        enterTransition = { enterSlide(SlideDirection.Up) },
        exitTransition = { exitSlide(SlideDirection.Down) },
        popEnterTransition = { enterSlide(SlideDirection.Up) },
        popExitTransition = { exitSlide(SlideDirection.Down) }
    ) {
        val navOptions = navOptions {
            popUpTo(Home::class.java.name) {
                inclusive = false
            }
        }
        DefectCompletionScreen(
            navigateToUp = navController::navigateUp,
            navigateToDefectDetail = { navController.navigateToDefectDetail(it, navOptions) }
        )
    }
}

fun NavController.navigateToDefectEntry() {
    navigate(DefectEntry)
}

fun NavController.navigateToDefectDetail(defectItem: DefectItem, navOptions: NavOptions? = null) {
    navigate(DefectDetail(defectItem = defectItem.toArgs()), navOptions = navOptions)
}

fun NavController.navigateToDefectCompletion(defectMainItem: DefectMainItem) {
    navigate(DefectCompletion(defectMainItem = defectMainItem))
}