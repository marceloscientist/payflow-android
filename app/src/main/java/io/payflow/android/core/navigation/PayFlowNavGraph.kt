package io.payflow.android.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.payflow.android.feature.auth.ui.LoginScreen
import io.payflow.android.feature.main.ui.MainScreen

@Composable
fun PayFlowNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {

        composable(Routes.Login.route) {

            LoginScreen(
                onLoginClick = {

                    navController.navigate(
                        Routes.Main.route
                    )
                }
            )
        }

        composable(
            Routes.Main.route
        ) {
            MainScreen()
        }
    }
}