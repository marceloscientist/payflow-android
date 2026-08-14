package io.payflow.android.core.session.repository

import io.payflow.android.core.session.model.UserSession

interface SessionRepository {

    fun saveSession(
        session: UserSession
    )

    fun getSession(): UserSession

    fun clearSession()
}
