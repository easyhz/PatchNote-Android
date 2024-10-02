package com.easyhz.patchnote.ui.navigation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easyhz.patchnote.ui.navigation.dataManagement.navigateToDataManagement
import com.easyhz.patchnote.ui.navigation.defect.navigateToDefectEntry
import com.easyhz.patchnote.ui.screen.home.HomeScreen

internal fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    composable<Home> {
        HomeScreen(
            navigateToDataManagement = navController::navigateToDataManagement,
            navigateToDefectEntry = navController::navigateToDefectEntry
        )
    }
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(Home, navOptions)
}