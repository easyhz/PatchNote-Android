package com.easyhz.patchnote.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.easyhz.patchnote.ui.navigation.home.Home
import com.easyhz.patchnote.ui.navigation.home.navigateToHome
import com.easyhz.patchnote.ui.navigation.offlineDefect.navigateToOfflineDefect
import com.easyhz.patchnote.ui.navigation.offlineDefect.screen.OfflineDefect
import com.easyhz.patchnote.ui.navigation.util.BottomMenuTabs


@Composable
internal fun rememberAppNavController(navController: NavHostController = rememberNavController()) =
    remember { AppNavController(navController = navController) }

class AppNavController(
    val navController: NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    private val routes: List<String>
        @Composable get() = remember { BottomMenuTabs.entries.map { it.qualifierName } }

    private val currentRoute: String?
        @Composable get() = currentDestination?.route?.split("?")?.firstOrNull()

    @Composable
    fun isInBottomTabs(): Boolean = currentRoute in routes

    @Composable
    fun mapRouteToTab(): BottomMenuTabs? {
        return when (currentRoute) {
            Home::class.java.name -> BottomMenuTabs.HOME
            OfflineDefect::class.java.name -> BottomMenuTabs.OFFLINE_DEFECT
            else -> null
        }
    }

    fun navigate(route: BottomMenuTabs) {
        if (route.qualifierName == navController.currentDestination?.route) return
        trace("Navigation: ${route.name}") {
            val navOptions = navOptions {
                popUpTo(navController.graph.id) {
                    saveState = true
                    inclusive = false
                }
                launchSingleTop  = true
                restoreState = true
            }
            when (route) {
                BottomMenuTabs.HOME -> navController.navigateToHome(navOptions = navOptions)
                BottomMenuTabs.OFFLINE_DEFECT -> navController.navigateToOfflineDefect(navOptions)
            }
        }
    }
}