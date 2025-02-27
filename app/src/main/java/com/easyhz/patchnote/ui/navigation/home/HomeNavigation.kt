package com.easyhz.patchnote.ui.navigation.home

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.easyhz.patchnote.core.designSystem.util.transition.SlideDirection
import com.easyhz.patchnote.core.designSystem.util.transition.enterSlide
import com.easyhz.patchnote.core.designSystem.util.transition.exitSlide
import com.easyhz.patchnote.core.model.filter.FilterParam
import com.easyhz.patchnote.ui.navigation.defect.navigateToDefectDetail
import com.easyhz.patchnote.ui.navigation.defect.navigateToDefectEntry
import com.easyhz.patchnote.ui.navigation.home.screen.DefectExport
import com.easyhz.patchnote.ui.navigation.onboarding.navigateToOnboarding
import com.easyhz.patchnote.ui.navigation.setting.navigateToSetting
import com.easyhz.patchnote.ui.screen.defect.export.DefectExportScreen
import com.easyhz.patchnote.ui.screen.filter.FilterScreen
import com.easyhz.patchnote.ui.screen.home.HomeScreen

internal fun NavGraphBuilder.homeGraph(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable<Home>(
        typeMap = Home.typeMap,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { exitSlide(SlideDirection.End) }
    ) {
        val args = it.toRoute<Home>()
        val navOptions = navOptions {
            popUpTo(navController.graph.id) { inclusive = true }
        }
        HomeScreen(
            modifier = modifier,
            filterParam = args.filterParam,
            navigateToSetting = navController::navigateToSetting,
            navigateToDefectEntry = navController::navigateToDefectEntry,
            navigateToFilter = navController::navigateToFilter,
            navigateToDefectDetail = navController::navigateToDefectDetail,
            navigateToDefectExport = navController::navigateToDefectExport,
            navigateToLogin = { navController.navigateToOnboarding(navOptions = navOptions) }
        )
    }

    composable<Filter>(
        typeMap = Filter.typeMap,
        enterTransition = { enterSlide(SlideDirection.Up) },
        exitTransition = { exitSlide(SlideDirection.Down) },
        popEnterTransition = { enterSlide(SlideDirection.Up) },
        popExitTransition = { exitSlide(SlideDirection.Down) }
    ) {
        val navOptions = navOptions {
            popUpTo(navController.graph.id) { inclusive = true }
        }
        val args = it.toRoute<Filter>()
        FilterScreen(
            filterParam = args.filterParam,
            navigateToUp = navController::navigateUp,
            navigateToHome = { item ->
                navController.navigateToHome(
                    filterParam = item,
                    navOptions = navOptions
                )
            }
        )
    }

    composable<DefectExport>(
        typeMap = DefectExport.typeMap,
        enterTransition = { enterSlide(SlideDirection.Up) },
        exitTransition = { exitSlide(SlideDirection.Down) },
        popEnterTransition = { enterSlide(SlideDirection.Up) },
        popExitTransition = { exitSlide(SlideDirection.Down) }
    ) {
        val args = it.toRoute<DefectExport>()
        DefectExportScreen(
            filterParam = args.filterParam,
            navigateToUp = navController::navigateUp
        )
    }
}

fun NavController.navigateToHome(
    filterParam: FilterParam = FilterParam.initToHome(), navOptions: NavOptions? = null
) {
    navigate(Home(filterParam), navOptions)
}

fun NavController.navigateToFilter(filterParam: FilterParam) {
    navigate(Filter(filterParam = filterParam))
}

fun NavController.navigateToDefectExport(filterParam: FilterParam) {
    navigate(DefectExport(filterParam = filterParam))
}