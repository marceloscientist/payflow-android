package io.payflow.android.feature.subscriptions.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.payflow.android.core.components.PayFlowButton
import io.payflow.android.core.components.PayFlowCard
import io.payflow.android.core.components.PayFlowConfirmationDialog
import io.payflow.android.core.components.PayFlowDropdown
import io.payflow.android.core.components.PayFlowLoadingState
import io.payflow.android.core.components.PayFlowServiceLogo
import io.payflow.android.core.components.PayFlowTextField
import io.payflow.android.core.components.PayFlowTopBar
import io.payflow.android.core.components.model.PayFlowButtonType
import io.payflow.android.core.state.UiState
import io.payflow.android.data.local.database.PayFlowDatabase
import io.payflow.android.data.remote.dto.ServiceDto
import io.payflow.android.data.repository.SubscriptionRepository
import io.payflow.android.feature.subscriptions.model.AddSubscriptionFormState
import io.payflow.android.feature.subscriptions.viewmodel.AddSubscriptionViewModel
import io.payflow.android.model.BillingFrequency
import io.payflow.android.model.Category
import java.text.NumberFormat
import java.util.Locale

@Composable
fun AddSubscriptionScreen(
    onSaved: () -> Unit,
    onNavigateBack: () -> Unit,
    editingSubscriptionId: String? = null
) {
    val context = LocalContext.current
    val subscriptionRepository = remember {
        val database = PayFlowDatabase.getInstance(context)

        SubscriptionRepository(database.subscriptionDao())
    }

    val viewModel: AddSubscriptionViewModel = viewModel(
        key = editingSubscriptionId?.let { "edit-subscription-$it" } ?: "add-subscription",
        factory = AddSubscriptionViewModel.factory(subscriptionRepository, editingSubscriptionId)
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val services by viewModel.services.collectAsStateWithLifecycle()
    val isCatalogLoading by viewModel.isCatalogLoading.collectAsStateWithLifecycle()
    val catalogErrorMessage by viewModel.catalogErrorMessage.collectAsStateWithLifecycle()

    val isEditing = editingSubscriptionId != null
    var showConfirmationDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        if (uiState is UiState.Success) {
            onSaved()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        PayFlowTopBar(
            title = if (isEditing) "Editar Assinatura" else "Nova Assinatura",
            onNavigateBack = onNavigateBack
        )

        when (uiState) {

            is UiState.Loading -> {
                PayFlowLoadingState(
                    message = if (isEditing) "Atualizando assinatura..." else "Salvando assinatura..."
                )
            }

            else -> {
                AddSubscriptionForm(
                    formState = formState,
                    services = services,
                    isCatalogLoading = isCatalogLoading,
                    catalogErrorMessage = catalogErrorMessage,
                    errorMessage = (uiState as? UiState.Error)?.message,
                    onServiceNameChange = viewModel::onServiceNameChange,
                    onCategoryChange = viewModel::onCategoryChange,
                    onPriceChange = viewModel::onPriceChange,
                    onBillingFrequencyChange = viewModel::onBillingFrequencyChange,
                    onBillingDayChange = viewModel::onBillingDayChange,
                    onServiceSelected = viewModel::selectService,
                    saveButtonLabel = if (isEditing) "Salvar alterações" else "Salvar assinatura",
                    onSaveClick = {
                        if (viewModel.validateForm()) {
                            showConfirmationDialog = true
                        }
                    }
                )
            }
        }
    }

    if (showConfirmationDialog) {
        PayFlowConfirmationDialog(
            title = if (isEditing) "Confirmar alterações" else "Confirmar cadastro",
            message = formState.toConfirmationMessage(isEditing),
            onConfirm = {
                showConfirmationDialog = false
                viewModel.save()
            },
            onDismiss = {
                showConfirmationDialog = false
            }
        )
    }
}

@Composable
private fun AddSubscriptionForm(
    formState: AddSubscriptionFormState,
    services: List<ServiceDto>,
    isCatalogLoading: Boolean,
    catalogErrorMessage: String?,
    errorMessage: String?,
    onServiceNameChange: (String) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onPriceChange: (String) -> Unit,
    onBillingFrequencyChange: (BillingFrequency) -> Unit,
    onBillingDayChange: (String) -> Unit,
    onServiceSelected: (ServiceDto) -> Unit,
    saveButtonLabel: String,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        PayFlowTextField(
            value = formState.serviceName,
            onValueChange = onServiceNameChange,
            label = "Nome do serviço",
            keyboardType = KeyboardType.Text,
            isError = formState.serviceNameError != null,
            supportingText = formState.serviceNameError
        )

        ServiceSuggestions(
            query = formState.serviceName,
            services = services,
            isCatalogLoading = isCatalogLoading,
            catalogErrorMessage = catalogErrorMessage,
            onServiceSelected = onServiceSelected
        )

        PayFlowDropdown(
            label = "Categoria",
            options = Category.entries.map { it.toLabel() },
            selectedOption = formState.category.toLabel(),
            onOptionSelected = { label ->
                Category.entries
                    .firstOrNull { it.toLabel() == label }
                    ?.let(onCategoryChange)
            }
        )

        PayFlowTextField(
            value = formState.priceInput,
            onValueChange = onPriceChange,
            label = "Valor (R$)",
            keyboardType = KeyboardType.Number,
            isError = formState.priceError != null,
            supportingText = formState.priceError
        )

        PayFlowDropdown(
            label = "Frequência de cobrança",
            options = BillingFrequency.entries.map { it.toLabel() },
            selectedOption = formState.billingFrequency.toLabel(),
            onOptionSelected = { label ->
                BillingFrequency.entries
                    .firstOrNull { it.toLabel() == label }
                    ?.let(onBillingFrequencyChange)
            }
        )

        PayFlowTextField(
            value = formState.billingDayInput,
            onValueChange = onBillingDayChange,
            label = "Dia de cobrança (1 a 31)",
            keyboardType = KeyboardType.Number,
            isError = formState.billingDayError != null,
            supportingText = formState.billingDayError
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        PayFlowButton(
            text = saveButtonLabel,
            type = PayFlowButtonType.PRIMARY,
            onClick = onSaveClick
        )
    }
}

/**
 * Sugestões do catálogo de serviços (STORY-002) exibidas enquanto o
 * usuário digita. Ao tocar em uma sugestão, nome, categoria e valor
 * são preenchidos automaticamente.
 */
@Composable
private fun ServiceSuggestions(
    query: String,
    services: List<ServiceDto>,
    isCatalogLoading: Boolean,
    catalogErrorMessage: String?,
    onServiceSelected: (ServiceDto) -> Unit
) {
    if (isCatalogLoading) {
        PayFlowCard(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                CircularProgressIndicator(modifier = Modifier.height(20.dp))
                Text(
                    text = "Carregando servicos...",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        return
    }

    if (catalogErrorMessage != null) {
        Text(
            text = catalogErrorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
        return
    }

    if (services.isEmpty()) return

    val trimmedQuery = query.trim()

    val suggestions = services
        .filter { service ->
            trimmedQuery.isBlank() || (
                service.name.contains(trimmedQuery, ignoreCase = true) &&
                    !service.name.equals(trimmedQuery, ignoreCase = true)
                )
        }

    if (suggestions.isEmpty()) return

    val brlFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    PayFlowCard(contentPadding = PaddingValues(vertical = 4.dp)) {
        suggestions.forEach { service ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onServiceSelected(service) }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                PayFlowServiceLogo(
                    serviceName = service.name,
                    logoUrl = service.logo,
                    size = 40.dp
                )

                Text(
                    text = service.name,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = brlFormat.format(service.price),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

private fun AddSubscriptionFormState.toConfirmationMessage(isEditing: Boolean = false): String {
    val brlFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    val price = parsedPrice()?.let(brlFormat::format) ?: priceInput

    val cadence = when (billingFrequency) {
        BillingFrequency.MONTHLY -> "todo dia ${billingDayInput.trim()}"
        BillingFrequency.YEARLY -> "uma vez ao ano, no dia ${billingDayInput.trim()}"
    }

    val verb = if (isEditing) "Atualizar" else "Cadastrar"

    return "$verb \"${serviceName.trim()}\" " +
        "(${category.toLabel()}) por $price " +
        "(${billingFrequency.toLabel()}), $cadence?"
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
