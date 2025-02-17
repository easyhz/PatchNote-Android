package com.easyhz.patchnote.ui.navigation.setting

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.easyhz.patchnote.core.designSystem.util.transition.SlideDirection
import com.easyhz.patchnote.core.designSystem.util.transition.enterSlide
import com.easyhz.patchnote.core.designSystem.util.transition.exitSlide
import com.easyhz.patchnote.ui.navigation.dataManagement.navigateToDataManagement
import com.easyhz.patchnote.ui.navigation.onboarding.navigateToOnboarding
import com.easyhz.patchnote.ui.screen.setting.main.SettingScreen
import com.easyhz.patchnote.ui.screen.setting.my_page.MyPageScreen
import com.easyhz.patchnote.ui.screen.setting.reception.ReceptionSettingScreen

internal fun NavGraphBuilder.settingGraph(
    navController: NavController
) {
    composable<Setting>(
        enterTransition = { enterSlide(SlideDirection.Start) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { exitSlide(SlideDirection.End) }
    ) {
        SettingScreen(
            navigateToDataManagement = navController::navigateToDataManagement,
            navigateToUp = navController::navigateUp,
            navigateToMyPage = navController::navigateToMyPage,
            navigateToReceptionSetting = navController::navigateToReceptionSetting,
        )
    }

    composable<MyPage> {
        val navOptions = navOptions {
            popUpTo(navController.graph.id) { inclusive = true }
        }
        MyPageScreen(
            navigateToUp = navController::navigateUp,
            navigateToOnboarding = { navController.navigateToOnboarding(navOptions = navOptions) },
        )
    }

    composable<ReceptionSetting> {
        ReceptionSettingScreen(
            navigateToUp = navController::navigateUp
        )
    }
}

fun NavController.navigateToSetting() {
    navigate(Setting)
}

fun NavController.navigateToMyPage() {
    navigate(MyPage)
}

fun NavController.navigateToReceptionSetting() {
    navigate(ReceptionSetting)
}