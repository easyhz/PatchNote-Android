package com.easyhz.patchnote.ui.navigation.image

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easyhz.patchnote.core.designSystem.util.transition.SlideDirection
import com.easyhz.patchnote.core.designSystem.util.transition.enterSlide
import com.easyhz.patchnote.core.designSystem.util.transition.exitSlide
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.DefectProgress
import com.easyhz.patchnote.ui.navigation.image.route.ImageDetail
import com.easyhz.patchnote.ui.navigation.image.route.ImageDetailArgs
import com.easyhz.patchnote.ui.screen.image.detail.ImageDetailScreen

internal fun NavGraphBuilder.imageGraph(
    navController: NavController
) {
    composable<ImageDetail>(
        typeMap = ImageDetail.typeMap,
        enterTransition = { enterSlide(SlideDirection.Up) },
        exitTransition = { exitSlide(SlideDirection.Down) },
        popEnterTransition = { enterSlide(SlideDirection.Up) },
        popExitTransition = { exitSlide(SlideDirection.Down) }
    ) {
        ImageDetailScreen(
            navigateUp = navController::navigateUp,
        )
    }
}

fun NavController.navigateToImageDetail(
    defectItem: DefectItem? = null,
    selectedTab: DefectProgress,
    currentImage: Int,
    navOptions: NavOptions? = null
) {
    if (defectItem == null) {
        return
    }
    val route = ImageDetail(
        ImageDetailArgs.create(
            defectItem = defectItem,
            selectedTab = selectedTab,
            currentImage = currentImage
        )
    )
    navigate(route, navOptions)
}
