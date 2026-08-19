package io.payflow.android.feature.subscriptions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.payflow.android.core.base.BaseViewModel
import io.payflow.android.core.state.UiState
import io.payflow.android.core.utils.Logger
import io.payflow.android.data.remote.dto.ServiceDto
import io.payflow.android.data.remote.repository.CatalogRepository
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
import kotlin.math.roundToLong
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
    private val subscriptionRepository: SubscriptionRepository,
    private val catalogRepository: CatalogRepository = CatalogRepository()
) : BaseViewModel<AddSubscriptionUiState>() {

    private val _formState = MutableStateFlow(AddSubscriptionFormState())
    val formState: StateFlow<AddSubscriptionFormState> = _formState.asStateFlow()

    private val _services = MutableStateFlow<List<ServiceDto>>(emptyList())
    val services: StateFlow<List<ServiceDto>> = _services.asStateFlow()

    private val _isCatalogLoading = MutableStateFlow(false)
    val isCatalogLoading: StateFlow<Boolean> = _isCatalogLoading.asStateFlow()

    private val _catalogErrorMessage = MutableStateFlow<String?>(null)
    val catalogErrorMessage: StateFlow<String?> = _catalogErrorMessage.asStateFlow()

    init {
        updateState(UiState.Empty)
        loadCatalog()
    }

    /**
     * Carrega o catálogo de serviços da API (STORY-002) para sugestões.
     * Falha silenciosa: as sugestões são um extra, o cadastro manual
     * continua funcionando sem elas.
     */
    private fun loadCatalog() = launch {
        _isCatalogLoading.value = true
        _catalogErrorMessage.value = null

        Logger.debug(TAG, "Iniciando carregamento do catalogo de servicos")

        runCatching {
            catalogRepository.getServices()
        }.onSuccess { services ->
            _services.value = services
            Logger.debug(
                TAG,
                "Catalogo carregado com ${services.size} servicos. Primeiros itens: ${services.take(3).joinToString { it.name }}"
            )
        }.onFailure { exception ->
            _catalogErrorMessage.value =
                exception.message ?: "Nao foi possivel carregar os servicos agora"
            Logger.error(
                TAG,
                "Falha ao carregar catalogo: ${exception.message}",
                exception
            )
        }

        _isCatalogLoading.value = false
    }

    /**
     * Preenche o formulário a partir de um serviço do catálogo.
     */
    fun selectService(service: ServiceDto) {
        val cents = (service.price * CENTS_IN_REAL).roundToLong()

        _formState.update {
            it.copy(
                selectedServiceId = service.id,
                selectedServiceLogoUrl = service.logo,
                serviceName = service.name,
                category = service.category.toCategoryOrDefault(it.category),
                priceInput = formatCurrencyInput(cents.toString()),
                serviceNameError = null,
                priceError = null
            )
        }
    }

    private fun String.toCategoryOrDefault(default: Category): Category =
        when (trim().uppercase()) {
            Category.STREAMING.name -> Category.STREAMING
            Category.MUSIC.name -> Category.MUSIC
            Category.GAMES.name,
            "GAMING" -> Category.GAMES
            Category.AI_PRODUCTIVITY.name -> Category.AI_PRODUCTIVITY
            Category.CLOUD_STORAGE.name -> Category.CLOUD_STORAGE
            Category.EDUCATION.name -> Category.EDUCATION
            else -> default
        }

    fun onServiceNameChange(value: String) {
        _formState.update {
            it.copy(
                selectedServiceId = null,
                selectedServiceLogoUrl = null,
                serviceName = value,
                serviceNameError = null
            )
        }
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
            serviceId = selectedServiceId ?: name.lowercase().replace(WHITESPACE_REGEX, "-"),
            serviceName = name,
            logoUrl = selectedServiceLogoUrl,
            category = category,
            price = requireNotNull(parsedPrice()) { "Valor inválido" },
            billingFrequency = billingFrequency,
            billingDay = requireNotNull(parsedBillingDay()) { "Dia de cobrança inválido" },
            status = SubscriptionStatus.ACTIVE,
            createdAt = System.currentTimeMillis()
        )
    }

    companion object {
        private const val TAG = "AddSubscriptionVM"

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
