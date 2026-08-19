package io.payflow.android.core.components.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.payflow.android.core.components.PayFlowButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.payflow.android.core.components.PayFlowCard
import io.payflow.android.core.components.PayFlowChip
import io.payflow.android.core.components.PayFlowConfirmationDialog
import io.payflow.android.core.components.PayFlowEmptyState
import io.payflow.android.core.components.PayFlowLoadingState
import io.payflow.android.core.components.PayFlowMetricCard
import io.payflow.android.core.components.PayFlowProfileHeader
import io.payflow.android.core.components.PayFlowSearchBar
import io.payflow.android.core.components.PayFlowSettingsItem
import io.payflow.android.core.components.PayFlowStatusBadge
import io.payflow.android.core.components.PayFlowSubscriptionCard
import io.payflow.android.core.components.PayFlowTextField
import io.payflow.android.core.components.PayFlowTopBar
import io.payflow.android.core.components.model.PayFlowButtonType
import io.payflow.android.core.components.model.PayFlowStatusType
import io.payflow.android.core.theme.PayFlowSpacing

@Composable
fun DesignSystemScreen() {

    var text by remember {
        mutableStateOf("")
    }

    var searchQuery by remember {
        mutableStateOf("")
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(
            PayFlowSpacing.MD
        )
    ) {

        Text("Developer Playground")

        PayFlowButton(
            text = "Salvar",
            type = PayFlowButtonType.PRIMARY,
            onClick = {}
        )

        PayFlowButton(
            text = "Editar",
            type = PayFlowButtonType.SECONDARY,
            onClick = {}
        )

        PayFlowButton(
            text = "Cancelar Assinatura",
            type = PayFlowButtonType.DANGER,
            onClick = {}
        )

        PayFlowTextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = "Digite algo"
        )

        PayFlowCard {

            Text(
                text = "Componente base utilizado para agrupar conteúdo."
            )
        }

        PayFlowMetricCard(
            title = "Gasto Mensal",
            value = "R$ 159,90"
        )

        PayFlowSubscriptionCard(
            serviceName = "Netflix",
            plan = "Premium",
            price = "R$ 44,90",
            billingInfo = "Mensal • Dia 10"
        )

        PayFlowSearchBar(
            query = searchQuery,
            onQueryChange = {
                searchQuery = it
            }
        )

        PayFlowLoadingState()

        PayFlowEmptyState(
            title = "Nenhuma assinatura encontrada",
            message = "Cadastre sua primeira assinatura."
        )

        PayFlowStatusBadge(
            type = PayFlowStatusType.ACTIVE
        )

        PayFlowStatusBadge(
            type = PayFlowStatusType.CANCELLED
        )

        PayFlowStatusBadge(
            type = PayFlowStatusType.PENDING
        )

        PayFlowStatusBadge(
            type = PayFlowStatusType.EXPIRED
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            PayFlowChip(
                label = "Streaming",
                onClick = {}
            )

            PayFlowChip(
                label = "Music",
                onClick = {}
            )

            PayFlowChip(
                label = "Games",
                onClick = {}
            )

            PayFlowChip(
                label = "AI",
                onClick = {}
            )
        }

        PayFlowTopBar(
            title = "PayFlow"
        )

        PayFlowProfileHeader(
            name = "Marcelo Santana",
            email = "marcelo@email.com"
        )

        PayFlowSettingsItem(
            title = "Notificações",
            onClick = {}
        )


        if (showDialog) {

            PayFlowConfirmationDialog(
                title = "Cancelar Assinatura",
                message = "Tem certeza que deseja cancelar esta assinatura?",
                onConfirm = {
                    showDialog = false
                },
                onDismiss = {
                    showDialog = false
                }
            )
        }


    }
}