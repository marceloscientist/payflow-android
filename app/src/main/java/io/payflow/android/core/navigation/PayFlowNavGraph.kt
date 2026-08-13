package io.payflow.android.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.payflow.android.core.session.model.UserSession
import io.payflow.android.core.session.repository.SessionRepositoryImpl
import io.payflow.android.feature.auth.ui.LoginScreen
import io.payflow.android.feature.main.ui.MainScreen

@Composable
fun PayFlowNavGraph() {

    val navController = rememberNavController()
    val sessionRepository = remember {
        SessionRepositoryImpl()
    }

    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {

        composable(Routes.Login.route) {

            val email = remember {
                mutableStateOf("")
            }

            val password = remember {
                mutableStateOf("")
            }

            LoginScreen(
                email = email.value,
                password = password.value,
                onEmailChange = {
                    email.value = it
                },
                onPasswordChange = {
                    password.value = it
                },
                onLoginClick = {

                    sessionRepository.saveSession(
                        UserSession(
                            name = email.value.substringBefore("@"),
                            email = email.value,
                            isLoggedIn = true
                        )
                    )

                    navController.navigate(
                        Routes.Main.route
                    )
                }
            )
        }

        composable(
            Routes.Main.route
        ) {
            MainScreen(
                sessionRepository = sessionRepository
            )
        }
    }
}