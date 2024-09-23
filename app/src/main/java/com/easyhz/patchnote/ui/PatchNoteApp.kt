package com.easyhz.patchnote.ui

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.easyhz.patchnote.core.designSystem.util.transition.SlideDirection
import com.easyhz.patchnote.core.designSystem.util.transition.enterSlide
import com.easyhz.patchnote.core.designSystem.util.transition.exitSlide
import com.easyhz.patchnote.ui.navigation.dataManagement.dataManagementGraph
import com.easyhz.patchnote.ui.navigation.home.Home
import com.easyhz.patchnote.ui.navigation.home.homeGraph
import com.easyhz.patchnote.ui.navigation.onboarding.onboardingGraph
import com.easyhz.patchnote.ui.navigation.sign.signGraph

@Composable
fun PatchNoteApp() {
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier.statusBarsPadding().systemBarsPadding(),
        navController = navController,
        startDestination = Home,
        enterTransition = { enterSlide(SlideDirection.Start) },
        exitTransition = { exitSlide(SlideDirection.Start) },
        popEnterTransition = { enterSlide(SlideDirection.End) },
        popExitTransition = { exitSlide(SlideDirection.End) }
    ) {
        onboardingGraph(navController)
        signGraph(navController)
        homeGraph(navController)
        dataManagementGraph(navController)
    }

}