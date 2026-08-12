package io.payflow.android.core.components.catalog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import io.payflow.android.core.components.PayFlowDropdown
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
            .padding(16.dp)
    ) {

        Text("Developer Playground")

        PayFlowButton(
            text = "Botão Primário",
            onClick = {}
        )

        PayFlowTextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = "Digite algo"
        )

        PayFlowCard() {
            Text(
                text = "PayFlow Card"
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
            text = "ACTIVE"
        )

        PayFlowChip(
            label = "Streaming",
            onClick = {}
        )

        PayFlowTopBar(
            title = "PayFlow"
        )

        PayFlowDropdown()

        PayFlowProfileHeader(
            name = "Marcelo Santana",
            email = "marcelo@email.com"
        )

        PayFlowSettingsItem(
            title = "Tema Escuro",
            onClick = {}
        )

        PayFlowButton(
            text = "Abrir Dialog",
            onClick = {
                showDialog = true
            }
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