package com.easyhz.patchnote.ui.navigation.dataManagement

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easyhz.patchnote.ui.screen.dataManagement.DataManagementScreen

internal fun NavGraphBuilder.dataManagementGraph(
    navController: NavController
) {
    composable<DataManagement> {
        DataManagementScreen(
            navigateToUp = navController::navigateUp
        )
    }
}