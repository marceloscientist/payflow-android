package io.payflow.android.feature.subscriptions.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.payflow.android.core.components.PayFlowButton
import io.payflow.android.core.components.PayFlowCard
import io.payflow.android.core.components.PayFlowConfirmationDialog
import io.payflow.android.core.components.PayFlowEmptyState
import io.payflow.android.core.components.PayFlowLoadingState
import io.payflow.android.core.components.PayFlowServiceLogo
import io.payflow.android.core.components.PayFlowStatusBadge
import io.payflow.android.core.components.PayFlowTopBar
import io.payflow.android.core.components.model.PayFlowButtonType
import io.payflow.android.core.components.model.PayFlowStatusType
import io.payflow.android.core.state.UiState
import io.payflow.android.core.theme.PayFlowSpacing
import io.payflow.android.data.local.database.PayFlowDatabase
import io.payflow.android.data.repository.SubscriptionRepository
import io.payflow.android.feature.subscriptions.viewmodel.SubscriptionDetailsViewModel
import io.payflow.android.model.BillingFrequency
import io.payflow.android.model.Category
import io.payflow.android.model.Subscription
import io.payflow.android.model.SubscriptionStatus
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun SubscriptionDetailsScreen(
    subscriptionId: String,
    onNavigateBack: () -> Unit,
    onEdit: () -> Unit,
    onDeleted: () -> Unit
) {
    val context = LocalContext.current
    val repository = remember {
        val database = PayFlowDatabase.getInstance(context)
        SubscriptionRepository(database.subscriptionDao())
    }
    val viewModel: SubscriptionDetailsViewModel = viewModel(
        key = "subscription-details-$subscriptionId",
        factory = SubscriptionDetailsViewModel.factory(subscriptionId, repository)
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        PayFlowTopBar(
            title = "Detalhes da Assinatura",
            onNavigateBack = onNavigateBack
        )
        when (val state = uiState) {
            UiState.Loading -> PayFlowLoadingState(
                message = "Carregando assinatura...",
                modifier = Modifier.fillMaxSize()
            )
            UiState.Empty -> PayFlowEmptyState(
                title = "Assinatura indisponível",
                message = "Nenhuma informação encontrada",
                modifier = Modifier.fillMaxSize()
            )
            is UiState.Error -> PayFlowEmptyState(
                title = "Erro ao carregar",
                message = state.message,
                modifier = Modifier.fillMaxSize()
            )
            is UiState.Success -> SubscriptionDetailsContent(
                subscription = state.data,
                onEdit = onEdit,
                onDelete = { showDeleteDialog = true }
            )
        }
    }

    if (showDeleteDialog) {
        PayFlowConfirmationDialog(
            title = "Excluir assinatura",
            message = "Tem certeza que deseja excluir esta assinatura? Esta ação não pode ser desfeita.",
            onConfirm = {
                showDeleteDialog = false
                viewModel.deleteSubscription(onDeleted)
            },
            onDismiss = { showDeleteDialog = false }
        )
    }
}

@Composable
private fun SubscriptionDetailsContent(
    subscription: Subscription,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(PayFlowSpacing.MD),
        verticalArrangement = Arrangement.spacedBy(PayFlowSpacing.MD)
    ) {
        PayFlowCard {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(PayFlowSpacing.MD),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                PayFlowServiceLogo(
                    serviceName = subscription.serviceName,
                    logoUrl = subscription.logoUrl,
                    size = 56.dp
                )

                Text(
                    text = subscription.serviceName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                PayFlowStatusBadge(type = subscription.status.toStatusType())
            }
            Spacer(modifier = Modifier.height(PayFlowSpacing.MD))
            DetailRow("Categoria", subscription.category.toLabel())
            DetailRow("Valor", subscription.price.toCurrency())
            DetailRow("Frequência de cobrança", subscription.billingFrequency.toLabel())
            DetailRow("Próxima cobrança", subscription.nextBillingDate())
            DetailRow("Status", subscription.status.toLabel())
            DetailRow("Data de cadastro", subscription.createdAt.toDateLabel())
        }
        PayFlowButton(
            text = "Editar",
            type = PayFlowButtonType.SECONDARY,
            onClick = onEdit
        )
        PayFlowButton(
            text = "Excluir assinatura",
            type = PayFlowButtonType.DANGER,
            onClick = onDelete
        )
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = PayFlowSpacing.SM)) {
        Text(text = label, style = MaterialTheme.typography.labelMedium)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}

private fun Double.toCurrency(): String =
    NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(this)

private fun Long.toDateLabel(): String =
    SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(Date(this))

private fun Subscription.nextBillingDate(): String {
    val now = Calendar.getInstance()
    val next = Calendar.getInstance().apply {
        timeInMillis = createdAt
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    when (billingFrequency) {
        BillingFrequency.MONTHLY -> {
            next.set(Calendar.YEAR, now.get(Calendar.YEAR))
            next.set(Calendar.MONTH, now.get(Calendar.MONTH))
            next.setSafeDay(billingDay)
            if (next.before(now)) {
                next.add(Calendar.MONTH, 1)
                next.setSafeDay(billingDay)
            }
        }
        BillingFrequency.YEARLY -> {
            next.set(Calendar.YEAR, now.get(Calendar.YEAR))
            next.setSafeDay(billingDay)
            if (next.before(now)) {
                next.add(Calendar.YEAR, 1)
                next.setSafeDay(billingDay)
            }
        }
    }
    return next.timeInMillis.toDateLabel()
}

private fun Calendar.setSafeDay(day: Int) {
    set(Calendar.DAY_OF_MONTH, 1)
    set(Calendar.DAY_OF_MONTH, day.coerceIn(1, getActualMaximum(Calendar.DAY_OF_MONTH)))
}

private fun Category.toLabel(): String = when (this) {
    Category.STREAMING -> "Streaming"
    Category.MUSIC -> "Música"
    Category.GAMES -> "Jogos"
    Category.AI_PRODUCTIVITY -> "IA e Produtividade"
    Category.CLOUD_STORAGE -> "Armazenamento em Nuvem"
    Category.EDUCATION -> "Educação"
    Category.OTHER -> "Outros"
}

private fun BillingFrequency.toLabel(): String = when (this) {
    BillingFrequency.MONTHLY -> "Mensal"
    BillingFrequency.YEARLY -> "Anual"
}

private fun SubscriptionStatus.toLabel(): String = when (this) {
    SubscriptionStatus.ACTIVE -> "Ativa"
    SubscriptionStatus.CANCELLED -> "Cancelada"
}

private fun SubscriptionStatus.toStatusType(): PayFlowStatusType = when (this) {
    SubscriptionStatus.ACTIVE -> PayFlowStatusType.ACTIVE
    SubscriptionStatus.CANCELLED -> PayFlowStatusType.CANCELLED
}
