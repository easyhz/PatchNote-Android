package com.easyhz.patchnote.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import com.easyhz.patchnote.core.designSystem.util.transition.SlideDirection
import com.easyhz.patchnote.core.designSystem.util.transition.enterSlide
import com.easyhz.patchnote.core.designSystem.util.transition.exitSlide
import com.easyhz.patchnote.ui.navigation.AppNavController
import com.easyhz.patchnote.ui.navigation.dataManagement.dataManagementGraph
import com.easyhz.patchnote.ui.navigation.defect.defectGraph
import com.easyhz.patchnote.ui.navigation.home.homeGraph
import com.easyhz.patchnote.ui.navigation.offlineDefect.offlineDefectGraph
import com.easyhz.patchnote.ui.navigation.onboarding.onboardingGraph
import com.easyhz.patchnote.ui.navigation.rememberAppNavController
import com.easyhz.patchnote.ui.navigation.setting.settingGraph
import com.easyhz.patchnote.ui.navigation.sign.signGraph
import com.easyhz.patchnote.ui.navigation.splash.Splash
import com.easyhz.patchnote.ui.navigation.splash.splashGraph
import com.easyhz.patchnote.ui.navigation.team.teamGraph
import com.easyhz.patchnote.ui.navigation.util.BottomBar
import com.easyhz.patchnote.ui.theme.LocalSnackBarHostState

@Composable
fun PatchNoteApp(
    appNavController: AppNavController = rememberAppNavController()
) {
    val navController = appNavController.navController
    val isVisibleBottomBar = appNavController.isInBottomTabs()
    val currentTab = appNavController.mapRouteToTab()

    CompositionLocalProvider(LocalSnackBarHostState provides SnackbarHostState()) {
        Scaffold(
            modifier = Modifier.systemBarsPadding(),
            bottomBar = {
                if (isVisibleBottomBar) {
                    BottomBar(
                        current = currentTab,
                        onSelected = appNavController::navigate
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                modifier = Modifier
                    .systemBarsPadding(),
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
                homeGraph(
                    modifier = Modifier
                        .padding(innerPadding),
                    navController = navController
                )
                offlineDefectGraph(
                    modifier = Modifier
                        .padding(innerPadding),
                    navController = navController
                )
                dataManagementGraph(navController)
                defectGraph(navController)
                settingGraph(navController)
                teamGraph(navController)
            }
        }
    }
}

fun NavController.navigateToSplash(navOptions: NavOptions?) {
    navigate(Splash, navOptions)
}