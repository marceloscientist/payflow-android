package io.payflow.android.data.auth.repository

interface AuthRepository {

    suspend fun login(
        email: String,
        password: String
    ): Result<AuthUser>

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<AuthUser>

    fun currentUser(): AuthUser?

    fun logout()
}

data class AuthUser(
    val uid: String,
    val name: String,
    val email: String
)

