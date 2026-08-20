package io.payflow.android.data.auth.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Result<AuthUser> = runCatching {
        val result = firebaseAuth
            .signInWithEmailAndPassword(email, password)
            .await()

        result.user?.toAuthUser()
            ?: throw IllegalStateException("Usuário não encontrado")
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<AuthUser> = runCatching {
        val result = firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .await()

        val user = result.user
            ?: throw IllegalStateException("Falha ao criar usuário")

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()

        user.updateProfile(profileUpdates).await()

        AuthUser(
            uid = user.uid,
            name = name,
            email = user.email.orEmpty()
        )
    }

    override fun currentUser(): AuthUser? {
        return firebaseAuth.currentUser?.toAuthUser()
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    private fun FirebaseUser.toAuthUser(): AuthUser {
        return AuthUser(
            uid = uid,
            name = displayName
                ?: email?.substringBefore("@").orEmpty(),
            email = email.orEmpty()
        )
    }
}

