package com.easyhz.patchnote.ui.navigation.sign

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.easyhz.patchnote.ui.navigation.home.navigateToHome
import com.easyhz.patchnote.ui.screen.sign.name.SignNameScreen
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
                navigateToVerificationId = navController::navigateToVerification,
                navigateToSignName = navController::navigateToName
            )
        }

        composable<Sign.Verification> {
            val args = it.toRoute<Sign.Verification>()
            SignVerificationScreen(
                verificationId = args.verificationId,
                phoneNumber = args.phoneNumber,
                navigateToUp = navController::navigateUp,
                navigateToName = navController::navigateToName
            )
        }

        composable<Sign.Name> {
            val args = it.toRoute<Sign.Name>()
            val navOptions = navOptions {
                popUpTo(navController.graph.id) { inclusive = true }
            }
            SignNameScreen(
                uid = args.uid,
                phoneNumber = args.phoneNumber,
                navigateToUp = navController::navigateUp,
                navigateToHome = { navController.navigateToHome(navOptions = navOptions) }
            )
        }
    }
}

fun NavController.navigateToSign() {
    navigate(Sign.Phone)
}

fun NavController.navigateToVerification(verificationId: String, phoneNumber: String) {
    navigate(Sign.Verification(verificationId, phoneNumber))
}

fun NavController.navigateToName(uid: String, phoneNumber: String) {
    navigate(Sign.Name(uid, phoneNumber))
}