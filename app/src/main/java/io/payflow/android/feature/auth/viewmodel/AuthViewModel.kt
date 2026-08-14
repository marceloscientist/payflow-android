package io.payflow.android.feature.auth.viewmodel

import io.payflow.android.core.base.BaseViewModel
import io.payflow.android.core.session.model.UserSession
import io.payflow.android.core.session.repository.SessionRepository
import io.payflow.android.core.state.UiState
import io.payflow.android.feature.auth.model.AuthState

class AuthViewModel(
    private val sessionRepository: SessionRepository
) : BaseViewModel<AuthState>() {

    fun updateEmail(
        value: String
    ) {
        val current =
            (uiState.value as? UiState.Success)?.data
                ?: AuthState()

        updateState(
            UiState.Success(
                current.copy(
                    email = value
                )
            )
        )
    }

    fun updatePassword(
        value: String
    ) {
        val current =
            (uiState.value as? UiState.Success)?.data
                ?: AuthState()

        updateState(
            UiState.Success(
                current.copy(
                    password = value
                )
            )
        )
    }

    fun login(
        email: String,
        password: String
    ): Boolean {

        if (
            email.isBlank() ||
            password.isBlank()
        ) {
            return false
        }

        launch {
            sessionRepository.saveSession(
                UserSession(
                    name = email.substringBefore("@"),
                    email = email,
                    isLoggedIn = true
                )
            )
        }

        return true
    }
}