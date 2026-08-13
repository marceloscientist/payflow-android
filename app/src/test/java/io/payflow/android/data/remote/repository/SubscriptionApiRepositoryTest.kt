package io.payflow.android.data.remote.repository

import io.payflow.android.data.remote.dto.ServiceDto
import io.payflow.android.model.Category
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SubscriptionApiRepositoryTest {

    @Test
    fun getSubscriptions_mapsRemoteServicesToDomain() = runBlocking {
        val repository = SubscriptionApiRepository(
            catalogRepository = FakeCatalogRepository(
                services = listOf(
                    ServiceDto(
                        id = "spotify",
                        name = "Spotify",
                        category = "MUSIC",
                        price = 21.9
                    )
                )
            )
        )

        val result = repository.getSubscriptions()

        assertTrue(result.isSuccess)

        val subscriptions = result.getOrThrow()

        assertEquals(1, subscriptions.size)
        assertEquals("Spotify", subscriptions.first().serviceName)
        assertEquals(Category.MUSIC, subscriptions.first().category)
        assertEquals(21.9, subscriptions.first().price, 0.0)
    }
}

private class FakeCatalogRepository(
    private val services: List<ServiceDto>
) : CatalogRepository() {

    override suspend fun getServices(): List<ServiceDto> {
        return services
    }
}