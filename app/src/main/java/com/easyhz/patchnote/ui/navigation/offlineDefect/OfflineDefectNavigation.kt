package com.easyhz.patchnote.ui.navigation.offlineDefect

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.easyhz.patchnote.core.designSystem.util.transition.SlideDirection
import com.easyhz.patchnote.core.designSystem.util.transition.exitSlide
import com.easyhz.patchnote.ui.navigation.defect.navigateToDefectDetail
import com.easyhz.patchnote.ui.navigation.offlineDefect.screen.OfflineDefect
import com.easyhz.patchnote.ui.navigation.onboarding.navigateToOnboarding
import com.easyhz.patchnote.ui.navigation.setting.navigateToSetting
import com.easyhz.patchnote.ui.screen.offline.defect.OfflineDefectScreen


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
            navigateToOfflineDefectDetail = { navController.navigateToDefectDetail(it) },
            navigateToSetting = navController::navigateToSetting,
            navigateToLogin = { navController.navigateToOnboarding(navOptions) }
        )
    }
}


fun NavController.navigateToOfflineDefect(navOptions: NavOptions? = null) {
    navigate(OfflineDefect, navOptions)
}