package io.payflow.android.core.session.model

data class UserSession(
    val name: String = "",
    val email: String = "",
    val isLoggedIn: Boolean = false
)