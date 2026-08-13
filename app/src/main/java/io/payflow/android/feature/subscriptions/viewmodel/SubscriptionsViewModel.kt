package io.payflow.android.feature.subscriptions.viewmodel

import io.payflow.android.core.base.BaseViewModel
import io.payflow.android.core.state.UiState
import io.payflow.android.feature.subscriptions.model.SubscriptionFilter
import io.payflow.android.feature.subscriptions.model.applyFilter
import io.payflow.android.model.BillingFrequency
import io.payflow.android.model.Category
import io.payflow.android.model.PaymentMethod
import io.payflow.android.model.Subscription
import io.payflow.android.model.SubscriptionStatus

class SubscriptionsViewModel : BaseViewModel<SubscriptionsUiState>() {

    // TODO: Substituir por SubscriptionRepository quando STORY-001 for entregue
    private val allSubscriptions: List<Subscription> = listOf(
        Subscription(
            id = "1",
            userId = "user1",
            serviceId = "netflix",
            serviceName = "Netflix",
            category = Category.STREAMING,
            plan = "Standard",
            price = 39.90,
            billingFrequency = BillingFrequency.MONTHLY,
            billingDay = 10,
            paymentMethod = PaymentMethod.CREDIT_CARD,
            status = SubscriptionStatus.ACTIVE,
            createdAt = System.currentTimeMillis()
        ),
        Subscription(
            id = "2",
            userId = "user1",
            serviceId = "spotify",
            serviceName = "Spotify",
            category = Category.MUSIC,
            plan = "Premium",
            price = 21.90,
            billingFrequency = BillingFrequency.MONTHLY,
            billingDay = 15,
            paymentMethod = PaymentMethod.CREDIT_CARD,
            status = SubscriptionStatus.ACTIVE,
            createdAt = System.currentTimeMillis()
        ),
        Subscription(
            id = "3",
            userId = "user1",
            serviceId = "disney",
            serviceName = "Disney+",
            category = Category.STREAMING,
            plan = "Standard",
            price = 27.90,
            billingFrequency = BillingFrequency.MONTHLY,
            billingDay = 5,
            paymentMethod = PaymentMethod.CREDIT_CARD,
            status = SubscriptionStatus.ACTIVE,
            createdAt = System.currentTimeMillis()
        ),
        Subscription(
            id = "4",
            userId = "user1",
            serviceId = "chatgpt",
            serviceName = "ChatGPT Plus",
            category = Category.AI_PRODUCTIVITY,
            plan = "Plus",
            price = 100.00,
            billingFrequency = BillingFrequency.MONTHLY,
            billingDay = 20,
            paymentMethod = PaymentMethod.CREDIT_CARD,
            status = SubscriptionStatus.CANCELLED,
            createdAt = System.currentTimeMillis(),
            cancelledAt = System.currentTimeMillis()
        ),
        Subscription(
            id = "5",
            userId = "user1",
            serviceId = "xbox",
            serviceName = "Xbox Game Pass",
            category = Category.GAMES,
            plan = "Ultimate",
            price = 44.99,
            billingFrequency = BillingFrequency.MONTHLY,
            billingDay = 8,
            paymentMethod = PaymentMethod.CREDIT_CARD,
            status = SubscriptionStatus.ACTIVE,
            createdAt = System.currentTimeMillis()
        )
    )

    private var currentFilter = SubscriptionFilter()

    init {
        loadSubscriptions()
    }

    fun loadSubscriptions() = launch {
        updateState(UiState.Loading)
        val filtered = allSubscriptions.applyFilter(currentFilter)
        if (filtered.isEmpty()) {
            updateState(UiState.Empty)
        } else {
            updateState(UiState.Success(SubscriptionsUiState(subscriptions = filtered)))
        }
    }

    fun onQueryChange(query: String) {
        currentFilter = currentFilter.copy(query = query)
        loadSubscriptions()
    }
}
