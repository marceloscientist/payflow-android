package io.payflow.android.feature.subscriptions.model

import io.payflow.android.model.Subscription

data class SubscriptionFilter(val query: String = "")

fun List<Subscription>.applyFilter(filter: SubscriptionFilter): List<Subscription> =
    if (filter.query.isBlank()) this
    else filter { it.serviceName.contains(filter.query, ignoreCase = true) }
