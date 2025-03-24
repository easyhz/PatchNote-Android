package com.easyhz.patchnote.ui.navigation.splash

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.easyhz.patchnote.ui.navigation.home.navigateToHome
import com.easyhz.patchnote.ui.navigation.onboarding.navigateToOnboarding
import com.easyhz.patchnote.ui.navigation.team.navigateToTeamSelection
import com.easyhz.patchnote.ui.screen.splash.SplashScreen

fun NavGraphBuilder.splashGraph(
    navController: NavController
) {
    composable<Splash> {
        val navOptions = navOptions {
            popUpTo(navController.graph.id) { inclusive = true }
        }
        SplashScreen(
            navigateToHome = { navController.navigateToHome(navOptions = navOptions) },
            navigateToOnboarding = { navController.navigateToOnboarding(navOptions = navOptions) },
            navigateToTeam = { navController.navigateToTeamSelection(navOptions = navOptions) }
        )
    }
}