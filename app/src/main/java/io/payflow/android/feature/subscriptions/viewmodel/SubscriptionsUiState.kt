package io.payflow.android.feature.subscriptions.viewmodel

import io.payflow.android.model.Subscription

data class SubscriptionsUiState(
    val subscriptions: List<Subscription> = emptyList()
)
