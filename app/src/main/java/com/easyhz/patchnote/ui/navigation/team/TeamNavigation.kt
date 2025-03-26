package com.easyhz.patchnote.ui.navigation.team

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.easyhz.patchnote.ui.navigation.home.navigateToHome
import com.easyhz.patchnote.ui.navigation.sign.navigateToTeam
import com.easyhz.patchnote.ui.navigation.team.screen.TeamMember
import com.easyhz.patchnote.ui.navigation.team.screen.TeamSelection
import com.easyhz.patchnote.ui.screen.team.member.TeamMemberScreen
import com.easyhz.patchnote.ui.screen.team.selection.TeamSelectionScreen

fun NavGraphBuilder.teamGraph(
    navController: NavController
) {
    composable<TeamSelection> {
        val navOptions = navOptions {
            popUpTo(navController.graph.id) { inclusive = true }
        }
        TeamSelectionScreen(
            navigateUp = navController::navigateUp,
            navigateToHome = { navController.navigateToHome(navOptions = navOptions) },
            navigateToSignTeam = { navController.navigateToTeam() }
        )
    }

    composable<TeamMember> {
        TeamMemberScreen(
            navigateUp = navController::navigateUp
        )
    }
}

fun NavController.navigateToTeamSelection(navOptions: NavOptions? = null) {
    navigate(TeamSelection, navOptions)
}

fun NavController.navigateToTeamMember(navOptions: NavOptions? = null) {
    navigate(TeamMember, navOptions)
}