package com.easyhz.patchnote.ui.navigation.team

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easyhz.patchnote.ui.navigation.team.screen.TeamSelection
import com.easyhz.patchnote.ui.screen.team.selection.TeamSelectionScreen

fun NavGraphBuilder.teamGraph(
    navController: NavController
) {
    composable<TeamSelection> {
        TeamSelectionScreen(
            navigateUp = navController::navigateUp,
        )
    }
}

fun NavController.navigateToTeamSelection(navOptions: NavOptions? = null) {
    navigate(TeamSelection, navOptions)
}