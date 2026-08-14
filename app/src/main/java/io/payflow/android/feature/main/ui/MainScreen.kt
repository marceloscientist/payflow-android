package io.payflow.android.feature.main.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.payflow.android.core.components.PayFlowBottomNavigation
import io.payflow.android.core.components.catalog.DesignSystemScreen
import io.payflow.android.core.navigation.Routes
import io.payflow.android.core.session.repository.SessionRepository
import io.payflow.android.feature.dashboard.ui.DashboardScreen
import io.payflow.android.feature.profile.ui.ProfileScreen
import io.payflow.android.feature.simulator.ui.SavingsSimulatorScreen
import io.payflow.android.feature.subscriptions.ui.SubscriptionsScreen

@Composable
fun MainScreen(
    sessionRepository: SessionRepository
) {

    val navController = rememberNavController()

    val currentRoute =
        navController.currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route

    Scaffold(
        bottomBar = {
            PayFlowBottomNavigation(
                navController = navController,
                currentRoute = currentRoute
            )
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Routes.Dashboard.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(Routes.Dashboard.route) {
                DashboardScreen(
                    sessionRepository = sessionRepository
                )
            }

            composable(Routes.Subscriptions.route) {
                SubscriptionsScreen()
            }

            composable(Routes.Simulator.route) {
                SavingsSimulatorScreen()
            }

            composable(Routes.Profile.route) {
                ProfileScreen(
                    sessionRepository = sessionRepository
                )
            }

            composable(Routes.DeveloperPlayground.route) {
                DesignSystemScreen()
            }
        }
    }
}