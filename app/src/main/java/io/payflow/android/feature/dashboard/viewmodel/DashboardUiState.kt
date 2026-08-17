package io.payflow.android.feature.dashboard.viewmodel

import io.payflow.android.model.BillingFrequency
import io.payflow.android.model.Subscription
import io.payflow.android.model.SubscriptionStatus
import kotlin.math.round

data class DashboardUiState(
    val monthlySpend: Double = 0.0,
    val annualSpend: Double = 0.0,
    val activeSubscriptionsCount: Int = 0,
    val nextDueSubscriptionName: String = "Nenhuma assinatura",
    val nextDueSubscriptionPrice: Double = 0.0,
    val nextDueSubscriptionBillingDay: Int = 0,
    val potentialSavings: Double = 0.0,
    val potentialSavingsServiceName: String = ""
) {

    companion object {

        fun fromSubscriptions(subscriptions: List<Subscription>): DashboardUiState {
            val activeSubscriptions = subscriptions.filter {
                it.status == SubscriptionStatus.ACTIVE
            }

            val monthlySpend = activeSubscriptions.sumOf { subscription ->
                when (subscription.billingFrequency) {
                    BillingFrequency.MONTHLY -> subscription.price
                    BillingFrequency.YEARLY -> subscription.price / 12.0
                }
            }

            val nextDueSubscription = activeSubscriptions.minByOrNull { subscription ->
                subscription.billingDay
            }

            val potentialSavingsSubscription = activeSubscriptions.maxByOrNull { subscription ->
                subscription.price
            }

            return DashboardUiState(
                monthlySpend = round(monthlySpend * 100.0) / 100.0,
                annualSpend = round(monthlySpend * 12.0 * 100.0) / 100.0,
                activeSubscriptionsCount = activeSubscriptions.size,
                nextDueSubscriptionName = nextDueSubscription?.serviceName ?: "Nenhuma assinatura",
                nextDueSubscriptionPrice = nextDueSubscription?.price ?: 0.0,
                nextDueSubscriptionBillingDay = nextDueSubscription?.billingDay ?: 0,
                potentialSavings = round((potentialSavingsSubscription?.price ?: 0.0) * 100.0) / 100.0,
                potentialSavingsServiceName = potentialSavingsSubscription?.serviceName ?: ""
            )
        }
    }
}
