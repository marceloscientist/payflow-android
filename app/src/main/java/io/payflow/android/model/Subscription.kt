package io.payflow.android.model

data class Subscription(
    val id: String,
    val userId: String,
    val serviceId: String,
    val serviceName: String,
    val category: Category,
    val plan: String? = null,
    val price: Double,
    val billingFrequency: BillingFrequency,
    val billingDay: Int,
    val paymentMethod: PaymentMethod? = null,
    val notes: String? = null,
    val status: SubscriptionStatus,
    val createdAt: Long,
    val cancelledAt: Long? = null
)