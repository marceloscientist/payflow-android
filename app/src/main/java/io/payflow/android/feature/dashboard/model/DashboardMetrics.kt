package io.payflow.android.feature.dashboard.model

data class DashboardMetrics(
    val monthlySpend: Double = 0.0,
    val annualSpend: Double = 0.0,
    val activeSubscriptionsCount: Int = 0,
    val nextDueSubscriptionName: String = "Nenhuma assinatura",
    val nextDueSubscriptionPrice: Double = 0.0,
    val nextDueSubscriptionBillingDay: Int = 0,
    val potentialSavings: Double = 0.0,
    val potentialSavingsServiceName: String = ""
)
