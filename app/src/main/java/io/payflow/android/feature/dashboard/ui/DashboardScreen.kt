package io.payflow.android.feature.dashboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.payflow.android.core.components.PayFlowCard
import io.payflow.android.core.components.PayFlowEmptyState
import io.payflow.android.core.components.PayFlowLoadingState
import io.payflow.android.core.components.PayFlowMetricCard
import io.payflow.android.core.components.PayFlowStatusBadge
import io.payflow.android.core.components.PayFlowTopBar
import io.payflow.android.core.components.model.PayFlowStatusType
import io.payflow.android.core.session.repository.SessionRepository
import io.payflow.android.core.state.UiState
import io.payflow.android.data.local.database.PayFlowDatabase
import io.payflow.android.data.repository.SubscriptionRepository
import io.payflow.android.feature.dashboard.viewmodel.DashboardUiState
import io.payflow.android.feature.dashboard.viewmodel.DashboardViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DashboardScreen(
    sessionRepository: SessionRepository,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val subscriptionRepository = remember {
        val database = PayFlowDatabase.getInstance(context)
        SubscriptionRepository(database.subscriptionDao())
    }

    val viewModel: DashboardViewModel = viewModel(
        factory = DashboardViewModel.factory(subscriptionRepository)
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        PayFlowTopBar(title = "Dashboard")

        when (val state = uiState) {
            is UiState.Loading -> {
                PayFlowLoadingState(
                    message = "Carregando dashboard...",
                    modifier = Modifier.fillMaxSize()
                )
            }

            is UiState.Empty -> {
                PayFlowEmptyState(
                    title = "Nenhuma assinatura cadastrada",
                    message = "Adicione suas assinaturas para visualizar o resumo financeiro.",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }

            is UiState.Error -> {
                PayFlowEmptyState(
                    title = "Erro ao carregar dashboard",
                    message = state.message,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }

            is UiState.Success -> {
                DashboardContent(
                    state = state.data,
                    userName = sessionRepository.getSession().name,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun DashboardContent(
    state: DashboardUiState,
    userName: String,
    modifier: Modifier = Modifier
) {
    val brlFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Olá, ${userName.ifBlank { "Usuário" }}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PayFlowMetricCard(
                title = "Gasto Mensal",
                value = brlFormat.format(state.monthlySpend),
                modifier = Modifier.weight(1f)
            )

            PayFlowMetricCard(
                title = "Gasto Anual",
                value = brlFormat.format(state.annualSpend),
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PayFlowMetricCard(
                title = "Assinaturas Ativas",
                value = state.activeSubscriptionsCount.toString(),
                modifier = Modifier.weight(1f)
            )

            PayFlowMetricCard(
                title = "Economia Potencial",
                value = brlFormat.format(state.potentialSavings),
                modifier = Modifier.weight(1f)
            )
        }

        PayFlowCard {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Próximo Vencimento",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = state.nextDueSubscriptionName,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = "Dia ${state.nextDueSubscriptionBillingDay} · ${brlFormat.format(state.nextDueSubscriptionPrice)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                PayFlowStatusBadge(type = PayFlowStatusType.ACTIVE)
            }
        }

        if (state.potentialSavingsServiceName.isNotBlank()) {
            Text(
                text = "Você pode economizar ${brlFormat.format(state.potentialSavings)} cancelando ${state.potentialSavingsServiceName}.",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}