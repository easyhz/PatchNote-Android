package com.easyhz.patchnote.ui.navigation.defect

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.toRoute
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
import com.easyhz.patchnote.ui.screen.defect.edit.DefectEditScreen
import com.easyhz.patchnote.ui.screen.defect.edit.DefectEditViewModel

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
        val args = it.toRoute<DefectDetail>()
        DefectDetailScreen(
            navigateToUp = {
                if (args.isRefresh) {
                    val navOptions = navOptions {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                    navController.navigateToHome(navOptions = navOptions)
                } else {
                    navController.navigateUp()
                }
            },
            navigateToDefectCompletion = navController::navigateToDefectCompletion,
            navigateToDefectEdit = navController::navigateToDefectEdit,
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
            navigateToDefectDetail = { navController.navigateToDefectDetail(defectItem = it, isRefresh = true, navOptions = navOptions) }
        )
    }

    composable<DefectEdit>(
        typeMap = DefectEdit.typeMap,
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
        DefectEditScreen(
            viewModel = hiltViewModel<DefectEditViewModel>(),
            navigateToUp = navController::navigateUp,
            navigateToDefectDetail = { navController.navigateToDefectDetail(defectItem = it, navOptions = navOptions) }
        )
    }
}

fun NavController.navigateToDefectEntry() {
    navigate(DefectEntry)
}

fun NavController.navigateToDefectDetail(defectItem: DefectItem? = null, isRefresh: Boolean = false, navOptions: NavOptions? = null) {
    if (defectItem == null) return
    navigate(DefectDetail(defectItem = defectItem.toArgs(), isRefresh = isRefresh), navOptions = navOptions)
}

fun NavController.navigateToDefectCompletion(defectMainItem: DefectMainItem) {
    navigate(DefectCompletion(defectMainItem = defectMainItem))
}

fun NavController.navigateToDefectEdit(defectItem: DefectItem) {
    navigate(DefectEdit(defectItem = defectItem.toArgs()))
}