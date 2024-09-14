package com.easyhz.patchnote.ui.navigation.sign

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.easyhz.patchnote.ui.screen.sign.phone.SignPhoneScreen
import com.easyhz.patchnote.ui.screen.sign.vericiation.SignVerificationScreen

internal fun NavGraphBuilder.signGraph(
    navController: NavController,
) {
    navigation<Sign>(
        startDestination = Sign.Phone
    ) {
        composable<Sign.Phone> {
            SignPhoneScreen(
                navigateToUp = navController::navigateUp,
                navigateToVerificationId = navController::navigateToVerification
            )
        }

        composable<Sign.Verification> {
            val args = it.toRoute<Sign.Verification>()
            SignVerificationScreen(
                verificationId = args.verificationId,
                navigateToUp = navController::navigateUp
            ) { }
        }
    }
}

fun NavController.navigateToSign() {
    navigate(Sign.Phone)
}

fun NavController.navigateToVerification(verificationId: String) {
    navigate(Sign.Verification(verificationId))
}