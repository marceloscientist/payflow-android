package io.payflow.android.data.local.mapper

import io.payflow.android.data.local.entity.SubscriptionEntity
import io.payflow.android.model.BillingFrequency
import io.payflow.android.model.Category
import io.payflow.android.model.PaymentMethod
import io.payflow.android.model.Subscription
import io.payflow.android.model.SubscriptionStatus

fun Subscription.toEntity(): SubscriptionEntity = SubscriptionEntity(
    id = id,
    userId = userId,
    serviceId = serviceId,
    serviceName = serviceName,
    category = category.name,
    plan = plan,
    price = price,
    billingFrequency = billingFrequency.name,
    billingDay = billingDay,
    paymentMethod = paymentMethod?.name,
    notes = notes,
    status = status.name,
    createdAt = createdAt,
    cancelledAt = cancelledAt
)

fun SubscriptionEntity.toDomain(): Subscription = Subscription(
    id = id,
    userId = userId,
    serviceId = serviceId,
    serviceName = serviceName,
    category = Category.valueOf(category),
    plan = plan,
    price = price,
    billingFrequency = BillingFrequency.valueOf(billingFrequency),
    billingDay = billingDay,
    paymentMethod = paymentMethod?.let { PaymentMethod.valueOf(it) },
    notes = notes,
    status = SubscriptionStatus.valueOf(status),
    createdAt = createdAt,
    cancelledAt = cancelledAt
)

