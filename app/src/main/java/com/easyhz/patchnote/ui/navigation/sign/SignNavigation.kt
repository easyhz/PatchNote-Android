package com.easyhz.patchnote.ui.navigation.sign

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.easyhz.patchnote.ui.screen.sign.phone.SignPhoneScreen

internal fun NavGraphBuilder.signGraph(
    navController: NavController,
) {
    navigation<Sign>(
        startDestination = Sign.Phone
    ) {
        composable<Sign.Phone> {
            SignPhoneScreen(
                navigateToUp = navController::navigateUp
            )
        }
    }
}

fun NavController.navigateToSign() {
    navigate(Sign.Phone)
}