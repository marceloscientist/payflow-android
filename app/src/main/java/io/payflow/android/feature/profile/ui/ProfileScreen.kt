package io.payflow.android.feature.profile.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.payflow.android.core.components.PayFlowCard
import io.payflow.android.core.components.PayFlowConfirmationDialog
import io.payflow.android.core.components.PayFlowEmptyState
import io.payflow.android.core.components.PayFlowLoadingState
import io.payflow.android.core.components.PayFlowProfileHeader
import io.payflow.android.core.components.PayFlowSettingsItem
import io.payflow.android.core.components.PayFlowTopBar
import io.payflow.android.core.session.repository.SessionRepository
import io.payflow.android.core.state.UiState
import io.payflow.android.core.theme.PayFlowSpacing
import io.payflow.android.feature.profile.viewmodel.ProfileUiState
import io.payflow.android.feature.profile.viewmodel.ProfileViewModel

private enum class SettingsDialog {
    NOTIFICATIONS,
    PRIVACY_POLICY,
    TERMS_OF_USE,
    ABOUT
}

@Composable
fun ProfileScreen(
    sessionRepository: SessionRepository,
    modifier: Modifier = Modifier
) {

    val viewModel: ProfileViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                ProfileViewModel(
                    sessionRepository = sessionRepository
                )
            }
        }
    )

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            PayFlowTopBar(
                title = "Perfil"
            )
        }
    ) { paddingValues ->

        when (val state = uiState) {

            is UiState.Loading -> {
                PayFlowLoadingState(
                    message = "Carregando perfil...",
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is UiState.Success -> {
                ProfileContent(
                    profile = state.data,
                    onToggleNotifications = viewModel::toggleNotifications,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is UiState.Error -> {
                PayFlowEmptyState(
                    title = "Ops, algo deu errado",
                    message = state.message,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is UiState.Empty -> {
                PayFlowEmptyState(
                    title = "Perfil indisponível",
                    message = "Nenhuma informação encontrada",
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
private fun ProfileContent(
    profile: ProfileUiState,
    onToggleNotifications: () -> Unit,
    modifier: Modifier = Modifier
) {

    var openDialog by remember {
        mutableStateOf<SettingsDialog?>(null)
    }

    val notificationsEnabled =
        profile.preferences.notificationsEnabled

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(PayFlowSpacing.MD)
    ) {

        PayFlowCard {
            PayFlowProfileHeader(
                name = profile.name,
                email = profile.email
            )
        }

        Spacer(
            modifier = Modifier.padding(
                PayFlowSpacing.SM
            )
        )

        Text(
            text = "Configurações",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                vertical = PayFlowSpacing.SM
            )
        )

        PayFlowCard(
            contentPadding = PaddingValues(0.dp)
        ) {

            PayFlowSettingsItem(
                title = "Notificações",
                onClick = {
                    openDialog = SettingsDialog.NOTIFICATIONS
                },
                trailingIcon = if (notificationsEnabled) {
                    Icons.Filled.NotificationsActive
                } else {
                    Icons.Filled.NotificationsOff
                },
                trailingIconTint = if (notificationsEnabled) {
                    Color(0xFF2E7D32)
                } else {
                    Color(0xFF616161)
                },
                trailingIconDescription = if (notificationsEnabled) {
                    "Notificações ativadas"
                } else {
                    "Notificações desativadas"
                }
            )

            PayFlowSettingsItem(
                title = "Política de Privacidade",
                onClick = {
                    openDialog = SettingsDialog.PRIVACY_POLICY
                }
            )

            PayFlowSettingsItem(
                title = "Termos de Uso",
                onClick = {
                    openDialog = SettingsDialog.TERMS_OF_USE
                }
            )

            PayFlowSettingsItem(
                title = "Sobre o Aplicativo",
                onClick = {
                    openDialog = SettingsDialog.ABOUT
                }
            )
        }

        Spacer(
            modifier = Modifier.padding(
                PayFlowSpacing.SM
            )
        )

        Text(
            text = "Informações do Aplicativo",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                vertical = PayFlowSpacing.SM
            )
        )

        PayFlowCard {

            Text(
                text = "PayFlow",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Versão: 1.0.0",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Equipe Responsável",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

    when (openDialog) {

        SettingsDialog.NOTIFICATIONS -> {
            PayFlowConfirmationDialog(
                title = "Notificações",
                message = if (notificationsEnabled) {
                    "As notificações estão ativadas. Deseja desativá-las?"
                } else {
                    "As notificações estão desativadas. Deseja ativá-las?"
                },
                onConfirm = {
                    onToggleNotifications()
                    openDialog = null
                },
                onDismiss = {
                    openDialog = null
                }
            )
        }

        SettingsDialog.PRIVACY_POLICY -> {
            PayFlowConfirmationDialog(
                title = "Política de Privacidade",
                message = "O PayFlow armazena seus dados apenas no dispositivo. " +
                        "Nenhuma informação pessoal é compartilhada com terceiros.",
                onConfirm = {
                    openDialog = null
                },
                onDismiss = {
                    openDialog = null
                }
            )
        }

        SettingsDialog.TERMS_OF_USE -> {
            PayFlowConfirmationDialog(
                title = "Termos de Uso",
                message = "O PayFlow é um aplicativo para organização pessoal de " +
                        "assinaturas digitais. Os valores exibidos são estimativas " +
                        "e não substituem os canais oficiais de cobrança.",
                onConfirm = {
                    openDialog = null
                },
                onDismiss = {
                    openDialog = null
                }
            )
        }

        SettingsDialog.ABOUT -> {
            PayFlowConfirmationDialog(
                title = "Sobre o Aplicativo",
                message = "PayFlow\nVersão: 1.0.0\n\nGerencie suas assinaturas " +
                        "em um único lugar.\n\nEquipe Responsável PayFlow.",
                onConfirm = {
                    openDialog = null
                },
                onDismiss = {
                    openDialog = null
                }
            )
        }

        null -> Unit
    }
}