package io.payflow.android.feature.profile.viewmodel

import io.payflow.android.core.base.BaseViewModel
import io.payflow.android.core.session.model.UserSession
import io.payflow.android.core.session.repository.SessionRepository
import io.payflow.android.core.state.UiState

class ProfileViewModel(
    private val sessionRepository: SessionRepository
) : BaseViewModel<ProfileUiState>() {

    init {
        loadProfile()
    }

    fun loadProfile() {
        launch {
            updateState(UiState.Loading)

            runCatching {
                sessionRepository.getSession()
            }.onSuccess { session ->
                updateState(
                    UiState.Success(
                        session.toProfileUiState()
                    )
                )
            }.onFailure {
                updateState(
                    UiState.Error(
                        "Não foi possível carregar o perfil"
                    )
                )
            }
        }
    }

    fun toggleNotifications() {
        val current = _uiState.value

        if (current is UiState.Success) {
            updateState(
                UiState.Success(
                    current.data.copy(
                        preferences = current.data.preferences.copy(
                            notificationsEnabled = !current.data.preferences.notificationsEnabled
                        )
                    )
                )
            )
        }
    }

    private fun UserSession.toProfileUiState(): ProfileUiState =
        if (isLoggedIn && email.isNotBlank()) {
            ProfileUiState(
                name = name.replaceFirstChar {
                    it.titlecase()
                },
                email = email
            )
        } else {
            ProfileUiState(
                name = DEFAULT_NAME,
                email = DEFAULT_EMAIL
            )
        }

    private companion object {
        const val DEFAULT_NAME = "Usuário PayFlow"
        const val DEFAULT_EMAIL = "usuario@payflow.app"
    }
}
