package io.payflow.android.feature.simulator.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.payflow.android.core.components.PayFlowButton
import io.payflow.android.core.components.PayFlowCard
import io.payflow.android.core.components.PayFlowEmptyState
import io.payflow.android.core.components.PayFlowLoadingState
import io.payflow.android.core.components.PayFlowMetricCard
import io.payflow.android.core.components.PayFlowSubscriptionCard
import io.payflow.android.core.components.PayFlowTopBar
import io.payflow.android.core.components.model.PayFlowButtonType
import io.payflow.android.core.state.UiState
import io.payflow.android.data.local.database.PayFlowDatabase
import io.payflow.android.data.repository.SubscriptionRepository
import io.payflow.android.feature.simulator.model.SubscriptionSimulatorData
import io.payflow.android.feature.simulator.viewmodel.SubscriptionSimulatorViewModel
import io.payflow.android.model.BillingFrequency
import io.payflow.android.model.Subscription
import java.text.NumberFormat
import java.util.Locale

@Composable
fun SavingsSimulatorScreen() {
    val context = LocalContext.current
    val repository = androidx.compose.runtime.remember {
        SubscriptionRepository(PayFlowDatabase.getInstance(context).subscriptionDao())
    }
    val viewModel: SubscriptionSimulatorViewModel = viewModel(
        factory = SubscriptionSimulatorViewModel.factory(repository)
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        PayFlowTopBar(title = "Simulador de economia")

        when (val state = uiState) {
            UiState.Loading -> PayFlowLoadingState(modifier = Modifier.fillMaxSize())
            UiState.Empty -> PayFlowEmptyState(
                title = "Nenhuma assinatura ativa",
                message = "Cadastre uma assinatura para simular sua economia.",
                modifier = Modifier.fillMaxSize()
            )
            is UiState.Error -> PayFlowEmptyState(
                title = "Erro ao carregar assinaturas",
                message = state.message,
                modifier = Modifier.fillMaxSize()
            )
            is UiState.Success -> SimulatorContent(
                data = state.data,
                onToggle = viewModel::toggleSubscription,
                onClear = viewModel::clearSelection
            )
        }
    }
}

@Composable
private fun SimulatorContent(
    data: SubscriptionSimulatorData,
    onToggle: (String) -> Unit,
    onClear: () -> Unit
) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Selecione assinaturas para simular o cancelamento",
                style = MaterialTheme.typography.titleMedium
            )
        }
        items(data.subscriptions, key = { it.id }) { subscription ->
            SubscriptionSelectionItem(
                subscription = subscription,
                selected = subscription in data.selectedSubscriptions,
                currencyFormat = currencyFormat,
                onToggle = { onToggle(subscription.id) }
            )
        }
        item {
            Spacer(modifier = Modifier.height(4.dp))
            PayFlowMetricCard(
                title = "Economia mensal",
                value = currencyFormat.format(data.monthlySavings)
            )
        }
        item {
            PayFlowMetricCard(
                title = "Economia anual",
                value = currencyFormat.format(data.annualSavings)
            )
        }
        item {
            PayFlowCard {
                Text("Resumo da simulação", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${data.selectedSubscriptions.size} assinatura(s) selecionada(s)",
                    style = MaterialTheme.typography.bodyLarge
                )
                data.selectedSubscriptions.forEach { subscription ->
                    Text(
                        text = subscription.serviceName,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
        if (data.selectedSubscriptions.isNotEmpty()) {
            item {
                PayFlowButton(
                    text = "Limpar seleção",
                    type = PayFlowButtonType.SECONDARY,
                    onClick = onClear
                )
            }
        }
    }
}

@Composable
private fun SubscriptionSelectionItem(
    subscription: Subscription,
    selected: Boolean,
    currencyFormat: NumberFormat,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = selected, onCheckedChange = { onToggle() })
        PayFlowSubscriptionCard(
            serviceName = subscription.serviceName,
            logoUrl = subscription.logoUrl,
            plan = subscription.plan ?: subscription.category.name,
            price = currencyFormat.format(subscription.price),
            billingInfo = subscription.billingFrequency.toSimulatorLabel(),
            modifier = Modifier.weight(1f)
        )
    }
}

private fun BillingFrequency.toSimulatorLabel(): String = when (this) {
    BillingFrequency.MONTHLY -> "Cobrança mensal"
    BillingFrequency.YEARLY -> "Cobrança anual"
}