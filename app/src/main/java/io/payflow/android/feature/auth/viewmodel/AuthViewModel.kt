package io.payflow.android.feature.auth.viewmodel

import io.payflow.android.core.base.BaseViewModel
import io.payflow.android.core.session.model.UserSession
import io.payflow.android.core.session.repository.SessionRepository
import io.payflow.android.core.state.UiState
import io.payflow.android.data.auth.repository.AuthRepository
import io.payflow.android.feature.auth.model.AuthState

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository
) : BaseViewModel<AuthState>() {

    init {
        updateState(UiState.Success(AuthState()))
    }

    private fun currentState(): AuthState =
        (uiState.value as? UiState.Success)?.data ?: AuthState()

    private fun update(transform: AuthState.() -> AuthState) {
        updateState(UiState.Success(currentState().transform()))
    }

    fun updateName(value: String) = update { copy(name = value, errorMessage = null) }
    fun updateEmail(value: String) = update { copy(email = value, errorMessage = null) }
    fun updatePassword(value: String) = update { copy(password = value, errorMessage = null) }
    fun updateConfirmPassword(value: String) = update { copy(confirmPassword = value, errorMessage = null) }

    fun resetForm() {
        updateState(UiState.Success(AuthState()))
    }

    fun login(onSuccess: () -> Unit) {
        val state = currentState()

        if (state.email.isBlank() || state.password.isBlank()) {
            update { copy(errorMessage = "Preencha e-mail e senha") }
            return
        }

        update { copy(isLoading = true, errorMessage = null) }

        launch {
            authRepository.login(state.email, state.password)
                .onSuccess { user ->
                    sessionRepository.saveSession(
                        UserSession(
                            name = user.name,
                            email = user.email,
                            isLoggedIn = true
                        )
                    )
                    update { copy(isLoading = false) }
                    onSuccess()
                }
                .onFailure { error ->
                    update {
                        copy(
                            isLoading = false,
                            errorMessage = error.localizedMessage
                                ?: "Falha ao entrar"
                        )
                    }
                }
        }
    }

    fun register(onSuccess: () -> Unit) {
        val state = currentState()

        when {
            state.name.isBlank() -> {
                update { copy(errorMessage = "Informe seu nome") }
                return
            }
            state.email.isBlank() -> {
                update { copy(errorMessage = "Informe seu e-mail") }
                return
            }
            state.password.length < 6 -> {
                update { copy(errorMessage = "A senha deve ter no mínimo 6 caracteres") }
                return
            }
            state.password != state.confirmPassword -> {
                update { copy(errorMessage = "As senhas não coincidem") }
                return
            }
        }

        update { copy(isLoading = true, errorMessage = null) }

        launch {
            authRepository.register(state.name, state.email, state.password)
                .onSuccess { user ->
                    sessionRepository.saveSession(
                        UserSession(
                            name = user.name,
                            email = user.email,
                            isLoggedIn = true
                        )
                    )
                    update { copy(isLoading = false) }
                    onSuccess()
                }
                .onFailure { error ->
                    update {
                        copy(
                            isLoading = false,
                            errorMessage = error.localizedMessage
                                ?: "Falha ao cadastrar"
                        )
                    }
                }
        }
    }
}

