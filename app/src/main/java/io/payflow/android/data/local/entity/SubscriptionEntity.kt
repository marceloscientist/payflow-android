package io.payflow.android.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriptions")
data class SubscriptionEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val serviceId: String,
    val serviceName: String,
    val logoUrl: String?,
    val category: String,
    val plan: String?,
    val price: Double,
    val billingFrequency: String,
    val billingDay: Int,
    val paymentMethod: String?,
    val notes: String?,
    val status: String,
    val createdAt: Long,
    val cancelledAt: Long?
)
