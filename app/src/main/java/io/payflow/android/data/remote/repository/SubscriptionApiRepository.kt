package io.payflow.android.data.remote.repository

import io.payflow.android.data.remote.dto.ServiceDto
import io.payflow.android.data.repository.BaseRepository
import io.payflow.android.model.BillingFrequency
import io.payflow.android.model.Category
import io.payflow.android.model.Subscription
import io.payflow.android.model.SubscriptionStatus

class SubscriptionApiRepository(
    private val catalogRepository: CatalogRepository = CatalogRepository()
) : BaseRepository() {

    suspend fun getSubscriptions(): Result<List<Subscription>> {
        return safeApiCall {
            catalogRepository.getServices().map { service ->
                service.toSubscription()
            }
        }
    }
}

private fun ServiceDto.toSubscription(): Subscription {
    val now = System.currentTimeMillis()

    return Subscription(
        id = id,
        userId = "remote-catalog",
        serviceId = id,
        serviceName = name,
        logoUrl = logo,
        category = category.toDomainCategory(),
        plan = null,
        price = price,
        billingFrequency = BillingFrequency.MONTHLY,
        billingDay = 1,
        paymentMethod = null,
        notes = "Importado do catalogo remoto PayFlow",
        status = SubscriptionStatus.ACTIVE,
        createdAt = now,
        cancelledAt = null
    )
}

private fun String.toDomainCategory(): Category {
    return when (trim().uppercase()) {
        Category.STREAMING.name -> Category.STREAMING
        Category.MUSIC.name -> Category.MUSIC
        Category.GAMES.name,
        "GAMING" -> Category.GAMES
        Category.AI_PRODUCTIVITY.name -> Category.AI_PRODUCTIVITY
        Category.CLOUD_STORAGE.name -> Category.CLOUD_STORAGE
        Category.EDUCATION.name -> Category.EDUCATION
        else -> Category.OTHER
    }
}
