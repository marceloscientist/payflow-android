package io.payflow.android.feature.auth.viewmodel

import io.payflow.android.feature.auth.model.AuthState

data class AuthUiState(
    val authState: AuthState = AuthState()
)