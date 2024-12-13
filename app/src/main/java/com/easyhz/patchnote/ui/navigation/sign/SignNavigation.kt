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
import com.easyhz.patchnote.ui.screen.sign.team.SignTeamScreen
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
            val navOptions = navOptions {
                popUpTo(navController.graph.id) { inclusive = true }
            }
            SignVerificationScreen(
                verificationId = args.verificationId,
                phoneNumber = args.phoneNumber,
                navigateToUp = navController::navigateUp,
                navigateToName = navController::navigateToName,
                navigateToHome = { navController.navigateToHome(navOptions = navOptions) }
            )
        }

        composable<Sign.Name> {
            val args = it.toRoute<Sign.Name>()
            SignNameScreen(
                uid = args.uid,
                phoneNumber = args.phoneNumber,
                navigateToUp = navController::navigateUp,
                navigateToTeam = navController::navigateToTeam
            )
        }

        composable<Sign.Team> {
            val navOptions = navOptions {
                popUpTo(navController.graph.id) { inclusive = true }
            }
            SignTeamScreen(
                navigateToHome = { navController.navigateToHome(navOptions = navOptions) },
                navigateToCreateTeam = { }
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

fun NavController.navigateToTeam(uid: String, phoneNumber: String, userName: String) {
    navigate(Sign.Team(uid, phoneNumber, userName))
}