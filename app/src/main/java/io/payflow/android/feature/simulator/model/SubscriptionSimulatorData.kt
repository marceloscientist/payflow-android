package io.payflow.android.feature.simulator.model

import io.payflow.android.model.Subscription

data class SubscriptionSimulatorData(
    val subscriptions: List<Subscription>,
    val selectedSubscriptions: List<Subscription>,
    val monthlySavings: Double,
    val annualSavings: Double
)