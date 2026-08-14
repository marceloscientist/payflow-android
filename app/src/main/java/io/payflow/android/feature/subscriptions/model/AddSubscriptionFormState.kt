package io.payflow.android.feature.subscriptions.model

import io.payflow.android.model.BillingFrequency
import io.payflow.android.model.Category

/**
 * Estado do formulário de cadastro de assinatura (STORY-004).
 *
 * Mantém os valores digitados pelo usuário e os erros de validação
 * de cada campo. A conversão para o domínio ocorre no ViewModel.
 */
data class AddSubscriptionFormState(
    val serviceName: String = "",
    val category: Category = Category.STREAMING,
    val priceInput: String = "",
    val billingFrequency: BillingFrequency = BillingFrequency.MONTHLY,
    val billingDayInput: String = "",
    val serviceNameError: String? = null,
    val priceError: String? = null,
    val billingDayError: String? = null
) {

    fun parsedPrice(): Double? =
        priceInput
            .trim()
            .replace(".", "")
            .replace(",", ".")
            .toDoubleOrNull()

    fun parsedBillingDay(): Int? =
        billingDayInput
            .trim()
            .toIntOrNull()
}
