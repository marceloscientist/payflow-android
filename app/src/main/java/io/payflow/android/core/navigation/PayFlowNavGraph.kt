package io.payflow.android.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.payflow.android.core.session.repository.SessionRepositoryImpl
import io.payflow.android.core.state.UiState
import io.payflow.android.data.auth.repository.FirebaseAuthRepository
import io.payflow.android.feature.auth.model.AuthState
import io.payflow.android.feature.auth.ui.LoginScreen
import io.payflow.android.feature.auth.ui.RegisterScreen
import io.payflow.android.feature.auth.viewmodel.AuthViewModel
import io.payflow.android.feature.main.ui.MainScreen

@Composable
fun PayFlowNavGraph() {

    val navController = rememberNavController()
    val sessionRepository = remember { SessionRepositoryImpl() }
    val authRepository = remember { FirebaseAuthRepository() }

    val factory = remember {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AuthViewModel(authRepository, sessionRepository) as T
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {

        composable(Routes.Login.route) {

            val viewModel: AuthViewModel = viewModel(factory = factory)
            val uiState by viewModel.uiState.collectAsState()
            val state = (uiState as? UiState.Success)?.data ?: AuthState()

            LoginScreen(
                email = state.email,
                password = state.password,
                isLoading = state.isLoading,
                errorMessage = state.errorMessage,
                onEmailChange = viewModel::updateEmail,
                onPasswordChange = viewModel::updatePassword,
                onLoginClick = {
                    viewModel.login {
                        navController.navigate(Routes.Main.route) {
                            popUpTo(Routes.Login.route) { inclusive = true }
                        }
                    }
                },
                onRegisterClick = {
                    viewModel.resetForm()
                    navController.navigate(Routes.Register.route)
                }
            )
        }

        composable(Routes.Register.route) {

            val viewModel: AuthViewModel = viewModel(factory = factory)
            val uiState by viewModel.uiState.collectAsState()
            val state = (uiState as? UiState.Success)?.data ?: AuthState()

            RegisterScreen(
                name = state.name,
                email = state.email,
                password = state.password,
                confirmPassword = state.confirmPassword,
                isLoading = state.isLoading,
                errorMessage = state.errorMessage,
                onNameChange = viewModel::updateName,
                onEmailChange = viewModel::updateEmail,
                onPasswordChange = viewModel::updatePassword,
                onConfirmPasswordChange = viewModel::updateConfirmPassword,
                onRegisterClick = {
                    viewModel.register {
                        navController.navigate(Routes.Main.route) {
                            popUpTo(Routes.Login.route) { inclusive = true }
                        }
                    }
                },
                onBackToLoginClick = {
                    viewModel.resetForm()
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.Main.route) {
            MainScreen(
                sessionRepository = sessionRepository
            )
        }
    }
}

