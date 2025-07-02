package com.easyhz.patchnote.ui.navigation.sign

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.easyhz.patchnote.ui.navigation.team.navigateToTeamSelection
import com.easyhz.patchnote.ui.screen.sign.name.SignNameScreen
import com.easyhz.patchnote.ui.screen.sign.phone.SignPhoneScreen
import com.easyhz.patchnote.ui.screen.sign.team.SignCreateTeamScreen
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
                phoneNumber = args.phoneNumber,
                navigateToUp = navController::navigateUp,
                navigateToName = navController::navigateToName,
                navigateToTeam = navController::navigateToTeam,
                navigateToTeamSelection = { navController.navigateToTeamSelection(navOptions = navOptions) }
            )
        }

        composable<Sign.Name> {
            SignNameScreen(
                navigateToUp = navController::navigateUp,
                navigateToTeam = navController::navigateToTeam
            )
        }

        composable<Sign.Team> {
            val navOptions = navOptions {
                popUpTo(navController.graph.id) { inclusive = true }
            }
            SignTeamScreen(
                navigateToTeamSelection = { navController.navigateToTeamSelection(navOptions = navOptions) },
                navigateToCreateTeam = navController::navigateToCreateTeam,
                navigateToUp = navController::navigateUp
            )
        }

        composable<Sign.CreateTeam> {
            val navOptions = navOptions {
                popUpTo(navController.graph.id) { inclusive = true }
            }
            SignCreateTeamScreen(
                navigateToTeamSelection = {  navController.navigateToTeamSelection(navOptions = navOptions) },
                navigateToUp = navController::navigateUp,
            )
        }
    }
}

fun NavController.navigateToSign() {
    navigate(Sign.Phone)
}

fun NavController.navigateToVerification(phoneNumber: String) {
    navigate(Sign.Verification(phoneNumber = phoneNumber))
}

fun NavController.navigateToName(uid: String, phoneNumber: String) {
    navigate(Sign.Name(uid = uid, phoneNumber = phoneNumber))
}

fun NavController.navigateToTeam() {
    navigate(Sign.Team)
}

fun NavController.navigateToCreateTeam() {
    navigate(Sign.CreateTeam)
}