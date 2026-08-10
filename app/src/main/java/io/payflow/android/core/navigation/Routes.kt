package io.payflow.android.core.navigation

sealed class Routes(
    val route: String
) {
    // Rotas Principais
    data object Dashboard : Routes("dashboard")
    data object Subscriptions : Routes("subscriptions")
    data object Simulator : Routes("simulator")
    data object Profile : Routes("profile")
    data object DeveloperPlayground : Routes("developer_playground")

    // Rotas Secundárias
    data object AddSubscription : Routes("add_subscription")
    data object SubscriptionDetails : Routes("subscription_details")
}