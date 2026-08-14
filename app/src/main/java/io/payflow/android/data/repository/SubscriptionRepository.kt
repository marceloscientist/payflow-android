package io.payflow.android.data.repository

import io.payflow.android.data.local.dao.SubscriptionDao
import io.payflow.android.data.local.mapper.toDomain
import io.payflow.android.data.local.mapper.toEntity
import io.payflow.android.model.Subscription
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SubscriptionRepository @Inject constructor(
    private val dao: SubscriptionDao
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
}

