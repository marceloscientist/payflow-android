package io.payflow.android.core.session.repository

import io.payflow.android.core.session.model.UserSession

class SessionRepositoryImpl : SessionRepository {

    private var currentSession = UserSession()

    override fun saveSession(
        session: UserSession
    ) {
        currentSession = session
    }

    override fun getSession(): UserSession {
        return currentSession
    }

    override fun clearSession() {
        currentSession = UserSession()
    }
}