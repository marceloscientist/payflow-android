package io.payflow.android.feature.subscriptions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.payflow.android.core.base.BaseViewModel
import io.payflow.android.core.state.UiState
import io.payflow.android.data.repository.SubscriptionRepository
import io.payflow.android.feature.subscriptions.model.AddSubscriptionFormState
import io.payflow.android.model.BillingFrequency
import io.payflow.android.model.Category
import io.payflow.android.model.Subscription
import io.payflow.android.model.SubscriptionStatus
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel do cadastro de assinatura (STORY-004).
 *
 * Fluxo: View -> ViewModel -> SubscriptionRepository -> Dao -> Room.
 *
 * O [uiState] representa o ciclo de persistência:
 * - [UiState.Empty]   formulário ocioso
 * - [UiState.Loading] salvando
 * - [UiState.Success] assinatura persistida
 * - [UiState.Error]   falha ao persistir
 */
class AddSubscriptionViewModel(
    private val subscriptionRepository: SubscriptionRepository
) : BaseViewModel<AddSubscriptionUiState>() {

    private val _formState = MutableStateFlow(AddSubscriptionFormState())
    val formState: StateFlow<AddSubscriptionFormState> = _formState.asStateFlow()

    init {
        updateState(UiState.Empty)
    }

    fun onServiceNameChange(value: String) {
        _formState.update { it.copy(serviceName = value, serviceNameError = null) }
    }

    fun onCategoryChange(category: Category) {
        _formState.update { it.copy(category = category) }
    }

    fun onPriceChange(value: String) {
        _formState.update {
            it.copy(priceInput = formatCurrencyInput(value), priceError = null)
        }
    }

    /**
     * Máscara monetária: os dígitos digitados são interpretados como
     * centavos e formatados no padrão pt-BR (ex: "4490" -> "44,90").
     */
    private fun formatCurrencyInput(input: String): String {
        val digits = input.filter(Char::isDigit).take(MAX_PRICE_DIGITS)

        if (digits.isEmpty()) return ""

        val cents = digits.toLong()

        return DecimalFormat("#,##0.00", DecimalFormatSymbols(Locale("pt", "BR")))
            .format(cents / CENTS_IN_REAL)
    }

    fun onBillingFrequencyChange(frequency: BillingFrequency) {
        _formState.update { it.copy(billingFrequency = frequency) }
    }

    fun onBillingDayChange(value: String) {
        _formState.update { it.copy(billingDayInput = value, billingDayError = null) }
    }

    /**
     * Valida o formulário, publica os erros por campo e
     * retorna se está apto a ser salvo.
     */
    fun validateForm(): Boolean {
        val form = _formState.value

        val serviceNameError = when {
            form.serviceName.isBlank() -> "Informe o nome do serviço"
            form.serviceName.trim().length < MIN_SERVICE_NAME_LENGTH ->
                "O nome deve ter ao menos $MIN_SERVICE_NAME_LENGTH caracteres"
            else -> null
        }

        val price = form.parsedPrice()
        val priceError = when {
            form.priceInput.isBlank() -> "Informe o valor"
            price == null -> "Valor inválido. Exemplo: 44,90"
            price <= 0.0 -> "O valor deve ser maior que zero"
            else -> null
        }

        val billingDay = form.parsedBillingDay()
        val billingDayError = when {
            form.billingDayInput.isBlank() -> "Informe o dia de cobrança"
            billingDay == null || billingDay !in MIN_BILLING_DAY..MAX_BILLING_DAY ->
                "O dia deve estar entre $MIN_BILLING_DAY e $MAX_BILLING_DAY"
            else -> null
        }

        _formState.update {
            it.copy(
                serviceNameError = serviceNameError,
                priceError = priceError,
                billingDayError = billingDayError
            )
        }

        return serviceNameError == null && priceError == null && billingDayError == null
    }

    fun save() = launch {
        if (!validateForm()) return@launch

        updateState(UiState.Loading)

        try {
            val subscription = _formState.value.toSubscription()
            subscriptionRepository.insert(subscription)
            updateState(UiState.Success(AddSubscriptionUiState(subscription)))
        } catch (exception: Exception) {
            updateState(
                UiState.Error(
                    message = exception.message ?: "Erro ao salvar assinatura"
                )
            )
        }
    }

    private fun AddSubscriptionFormState.toSubscription(): Subscription {
        val name = serviceName.trim()

        return Subscription(
            id = UUID.randomUUID().toString(),
            userId = DEFAULT_USER_ID,
            serviceId = name.lowercase().replace(WHITESPACE_REGEX, "-"),
            serviceName = name,
            category = category,
            price = requireNotNull(parsedPrice()) { "Valor inválido" },
            billingFrequency = billingFrequency,
            billingDay = requireNotNull(parsedBillingDay()) { "Dia de cobrança inválido" },
            status = SubscriptionStatus.ACTIVE,
            createdAt = System.currentTimeMillis()
        )
    }

    companion object {

        private const val DEFAULT_USER_ID = "local-user"
        private const val MIN_SERVICE_NAME_LENGTH = 2
        private const val MIN_BILLING_DAY = 1
        private const val MAX_BILLING_DAY = 31
        private const val MAX_PRICE_DIGITS = 7
        private const val CENTS_IN_REAL = 100.0
        private val WHITESPACE_REGEX = Regex("\\s+")

        fun factory(
            subscriptionRepository: SubscriptionRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(AddSubscriptionViewModel::class.java)) {
                        return AddSubscriptionViewModel(subscriptionRepository) as T
                    }

                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            }
    }
}
