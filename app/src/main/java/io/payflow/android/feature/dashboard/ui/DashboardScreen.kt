package io.payflow.android.feature.dashboard.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.payflow.android.core.session.repository.SessionRepository

@Composable
fun DashboardScreen(
    sessionRepository: SessionRepository,
    modifier: Modifier = Modifier
) {

    Text(
        text = "Olá, ${sessionRepository.getSession().name}",
        modifier = modifier
    )
}