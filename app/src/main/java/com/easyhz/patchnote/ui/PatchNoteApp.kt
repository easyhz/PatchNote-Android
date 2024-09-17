package com.easyhz.patchnote.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.easyhz.patchnote.core.designSystem.util.transition.SlideDirection
import com.easyhz.patchnote.core.designSystem.util.transition.enterSlide
import com.easyhz.patchnote.core.designSystem.util.transition.exitSlide
import com.easyhz.patchnote.ui.navigation.home.homeGraph
import com.easyhz.patchnote.ui.navigation.onboarding.Onboarding
import com.easyhz.patchnote.ui.navigation.onboarding.onboardingGraph
import com.easyhz.patchnote.ui.navigation.sign.signGraph

@Composable
fun PatchNoteApp() {
    val navController = rememberNavController()
    Scaffold { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Onboarding,
            enterTransition = { enterSlide(SlideDirection.Start) },
            exitTransition = { exitSlide(SlideDirection.Start) },
            popEnterTransition = { enterSlide(SlideDirection.End) },
            popExitTransition = { exitSlide(SlideDirection.End) }
        ) {
            onboardingGraph(navController)
            signGraph(navController)
            homeGraph(navController)
        }
    }
}