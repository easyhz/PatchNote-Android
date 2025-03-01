package com.easyhz.patchnote.ui.navigation.offlineDefect

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.easyhz.patchnote.core.designSystem.util.transition.SlideDirection
import com.easyhz.patchnote.core.designSystem.util.transition.enterSlide
import com.easyhz.patchnote.core.designSystem.util.transition.exitSlide
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.ui.navigation.defect.toArgs
import com.easyhz.patchnote.ui.navigation.offlineDefect.screen.OfflineDefect
import com.easyhz.patchnote.ui.navigation.offlineDefect.screen.OfflineDefectDetail
import com.easyhz.patchnote.ui.navigation.offlineDefect.screen.OfflineDefectEdit
import com.easyhz.patchnote.ui.navigation.onboarding.navigateToOnboarding
import com.easyhz.patchnote.ui.navigation.setting.navigateToSetting
import com.easyhz.patchnote.ui.screen.defect.edit.DefectEditScreen
import com.easyhz.patchnote.ui.screen.defect.offline.defect.OfflineDefectScreen
import com.easyhz.patchnote.ui.screen.defect.offline.detail.OfflineDefectDetailScreen
import com.easyhz.patchnote.ui.screen.defect.offline.edit.OfflineDefectEditViewModel


internal fun NavGraphBuilder.offlineDefectGraph(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable<OfflineDefect>(
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { exitSlide(SlideDirection.End) }
    ) {
        val navOptions = navOptions {
            popUpTo(navController.graph.id) { inclusive = true }
        }
        OfflineDefectScreen(
            modifier = modifier,
            navigateToOfflineDefectDetail = { navController.navigateToOfflineDefectDetail(it) },
            navigateToSetting = navController::navigateToSetting,
            navigateToLogin = { navController.navigateToOnboarding(navOptions) }
        )
    }

    composable<OfflineDefectDetail>(
        typeMap = OfflineDefectDetail.typeMap,
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { exitSlide(SlideDirection.End) }
    ) {
        OfflineDefectDetailScreen(
            navigateToUp = navController::navigateUp,
            navigateToDefectEdit = navController::navigateToOfflineDefectEdit,
        )
    }

    composable<OfflineDefectEdit>(
        typeMap = OfflineDefectEdit.typeMap,
        enterTransition = { enterSlide(SlideDirection.Up) },
        exitTransition = { exitSlide(SlideDirection.Down) },
        popEnterTransition = { enterSlide(SlideDirection.Up) },
        popExitTransition = { exitSlide(SlideDirection.Down) }
    ) {
        val navOptions = navOptions {
            popUpTo(OfflineDefect::class.java.name) {
                inclusive = false
            }
        }

        DefectEditScreen(
            viewModel = hiltViewModel<OfflineDefectEditViewModel>(),
            navigateToUp = navController::navigateUp,
            navigateToDefectDetail = { navController.navigateToOfflineDefectDetail(defectItem = it, navOptions = navOptions) }
        )
    }
}


fun NavController.navigateToOfflineDefect(navOptions: NavOptions? = null) {
    navigate(OfflineDefect, navOptions)
}

fun NavController.navigateToOfflineDefectDetail(defectItem: DefectItem, navOptions: NavOptions? = null) {
    navigate(OfflineDefectDetail(defectItem.toArgs()), navOptions)
}

fun NavController.navigateToOfflineDefectEdit(defectItem: DefectItem, navOptions: NavOptions? = null) {
    navigate(OfflineDefectEdit(defectItem.toArgs()), navOptions)
}