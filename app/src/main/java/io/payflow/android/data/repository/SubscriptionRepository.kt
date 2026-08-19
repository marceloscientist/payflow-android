package io.payflow.android.data.repository

import io.payflow.android.core.utils.Logger
import io.payflow.android.data.local.dao.SubscriptionDao
import io.payflow.android.data.local.mapper.toDomain
import io.payflow.android.data.local.mapper.toEntity
import io.payflow.android.data.remote.dto.ServiceDto
import io.payflow.android.data.remote.repository.CatalogRepository
import io.payflow.android.model.Category
import io.payflow.android.model.Subscription
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SubscriptionRepository @Inject constructor(
    private val dao: SubscriptionDao,
    private val catalogRepository: CatalogRepository = CatalogRepository()
) {

    fun getAll(): Flow<List<Subscription>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }

    suspend fun getById(id: String): Subscription? =
        dao.getById(id)?.toDomain()

    suspend fun insert(subscription: Subscription) =
        dao.insert(subscription.toEntity())

    suspend fun update(subscription: Subscription) =
        dao.update(subscription.toEntity())

    suspend fun delete(subscription: Subscription) =
        dao.delete(subscription.toEntity())

    suspend fun syncCatalogBackedSubscriptions() {
        runCatching {
            val services = catalogRepository.getServices()
            val servicesById = services.associateBy { it.id.normalizeKey() }
            val servicesByName = services.associateBy { it.name.normalizeKey() }

            dao.getAllSnapshot()
                .map { it.toDomain() }
                .forEach { subscription ->
                    val service = servicesById[subscription.serviceId.normalizeKey()]
                        ?: servicesByName[subscription.serviceName.normalizeKey()]
                        ?: return@forEach

                    val updatedSubscription = subscription.mergeWithService(service)

                    if (updatedSubscription != subscription) {
                        dao.update(updatedSubscription.toEntity())
                    }
                }
        }.onFailure { exception ->
            Logger.error(
                TAG,
                "Falha ao sincronizar assinaturas locais com o catalogo remoto",
                exception
            )
        }
    }

    private fun Subscription.mergeWithService(service: ServiceDto): Subscription =
        copy(
            serviceId = service.id,
            serviceName = service.name,
            logoUrl = service.logo,
            category = service.category.toCategoryOrDefault(category),
            price = service.price
        )

    private fun String.toCategoryOrDefault(default: Category): Category =
        when (trim().uppercase()) {
            Category.STREAMING.name -> Category.STREAMING
            Category.MUSIC.name -> Category.MUSIC
            Category.GAMES.name,
            "GAMING" -> Category.GAMES
            Category.AI_PRODUCTIVITY.name -> Category.AI_PRODUCTIVITY
            Category.CLOUD_STORAGE.name -> Category.CLOUD_STORAGE
            Category.EDUCATION.name -> Category.EDUCATION
            else -> default
        }

    private fun String.normalizeKey(): String = trim().lowercase()

    private companion object {
        private const val TAG = "SubscriptionRepository"
    }
}

