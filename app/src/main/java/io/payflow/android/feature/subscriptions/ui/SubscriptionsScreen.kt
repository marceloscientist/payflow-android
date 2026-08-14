package io.payflow.android.feature.subscriptions.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import io.payflow.android.core.components.PayFlowEmptyState
import io.payflow.android.core.components.PayFlowLoadingState
import io.payflow.android.core.components.PayFlowSearchBar
import io.payflow.android.core.components.PayFlowStatusBadge
import io.payflow.android.core.components.PayFlowSubscriptionCard
import io.payflow.android.core.components.PayFlowTopBar
import io.payflow.android.core.components.model.PayFlowStatusType
import io.payflow.android.core.state.UiState
import io.payflow.android.data.local.database.PayFlowDatabase
import io.payflow.android.data.repository.SubscriptionRepository
import io.payflow.android.feature.subscriptions.viewmodel.SubscriptionsUiState
import io.payflow.android.feature.subscriptions.viewmodel.SubscriptionsViewModel
import io.payflow.android.model.BillingFrequency
import io.payflow.android.model.Subscription
import io.payflow.android.model.SubscriptionStatus
import java.text.NumberFormat
import java.util.Locale

@Composable
fun SubscriptionsScreen(
    onSubscriptionClick: (String) -> Unit
) {
    val context = LocalContext.current
    val subscriptionRepository = androidx.compose.runtime.remember {
        val database = Room.databaseBuilder(
            context.applicationContext,
            PayFlowDatabase::class.java,
            "payflow.db"
        ).build()

        SubscriptionRepository(database.subscriptionDao())
    }

    val viewModel: SubscriptionsViewModel = viewModel(
        factory = SubscriptionsViewModel.factory(subscriptionRepository)
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var query by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {

        PayFlowTopBar(title = "Assinaturas")

        PayFlowSearchBar(
            query = query,
            onQueryChange = { newQuery ->
                query = newQuery
                viewModel.onQueryChange(newQuery)
            },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        when (val state = uiState) {

            is UiState.Loading -> {
                PayFlowLoadingState()
            }

            is UiState.Empty -> {
                PayFlowEmptyState(
                    title = "Nenhuma assinatura encontrada",
                    message = if (query.isBlank())
                        "Você ainda não possui assinaturas cadastradas."
                    else
                        "Nenhum resultado para \"$query\"."
                )
            }

            is UiState.Error -> {
                PayFlowEmptyState(
                    title = "Erro ao carregar",
                    message = state.message
                )
            }

            is UiState.Success -> {
                SubscriptionList(
                    data = state.data,
                    onSubscriptionClick = onSubscriptionClick
                )
            }
        }
    }
}

@Composable
private fun SubscriptionList(
    data: SubscriptionsUiState,
    onSubscriptionClick: (String) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            items = data.subscriptions,
            key = { it.id }
        ) { subscription ->
            SubscriptionListItem(
                subscription = subscription,
                onSubscriptionClick = onSubscriptionClick
            )
        }
    }
}

@Composable
private fun SubscriptionListItem(
    subscription: Subscription,
    onSubscriptionClick: (String) -> Unit
) {
    val brlFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onSubscriptionClick(subscription.id) }
    ) {
        PayFlowSubscriptionCard(
            serviceName = subscription.serviceName,
            plan = subscription.plan ?: subscription.category.name,
            price = brlFormat.format(subscription.price),
            billingInfo = "${subscription.billingFrequency.toLabel()} · Todo dia ${subscription.billingDay}"
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 12.dp, end = 12.dp)
        ) {
            PayFlowStatusBadge(type = subscription.status.toStatusType())
        }
    }
}

private fun BillingFrequency.toLabel(): String = when (this) {
    BillingFrequency.MONTHLY -> "Mensal"
    BillingFrequency.YEARLY -> "Anual"
}

private fun SubscriptionStatus.toStatusType(): PayFlowStatusType = when (this) {
    SubscriptionStatus.ACTIVE -> PayFlowStatusType.ACTIVE
    SubscriptionStatus.CANCELLED -> PayFlowStatusType.CANCELLED
}
