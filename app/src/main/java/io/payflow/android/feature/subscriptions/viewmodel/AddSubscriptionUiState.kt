package io.payflow.android.feature.subscriptions.viewmodel

import io.payflow.android.model.Subscription

/**
 * Dados de sucesso do cadastro de assinatura (STORY-004).
 */
data class AddSubscriptionUiState(
    val savedSubscription: Subscription
)
