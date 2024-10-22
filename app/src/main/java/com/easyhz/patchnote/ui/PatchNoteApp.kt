package com.easyhz.patchnote.ui

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.easyhz.patchnote.core.designSystem.util.transition.SlideDirection
import com.easyhz.patchnote.core.designSystem.util.transition.enterSlide
import com.easyhz.patchnote.core.designSystem.util.transition.exitSlide
import com.easyhz.patchnote.ui.navigation.dataManagement.dataManagementGraph
import com.easyhz.patchnote.ui.navigation.defect.defectGraph
import com.easyhz.patchnote.ui.navigation.home.homeGraph
import com.easyhz.patchnote.ui.navigation.onboarding.onboardingGraph
import com.easyhz.patchnote.ui.navigation.sign.signGraph
import com.easyhz.patchnote.ui.navigation.splash.Splash
import com.easyhz.patchnote.ui.navigation.splash.splashGraph
import com.easyhz.patchnote.ui.theme.LocalSnackBarHostState

@Composable
fun PatchNoteApp() {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalSnackBarHostState provides SnackbarHostState()) {
        NavHost(
            modifier = Modifier.statusBarsPadding().systemBarsPadding(),
            navController = navController,
            startDestination = Splash,
            enterTransition = { enterSlide(SlideDirection.Start) },
            exitTransition = { exitSlide(SlideDirection.Start) },
            popEnterTransition = { enterSlide(SlideDirection.End) },
            popExitTransition = { exitSlide(SlideDirection.End) }
        ) {
            splashGraph(navController)
            onboardingGraph(navController)
            signGraph(navController)
            homeGraph(navController)
            dataManagementGraph(navController)
            defectGraph(navController)
        }
    }
}