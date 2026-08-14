package io.payflow.android.feature.profile.viewmodel

import io.payflow.android.feature.profile.model.UserPreferences

data class ProfileUiState(
    val name: String,
    val email: String,
    val preferences: UserPreferences = UserPreferences()
)
