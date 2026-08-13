package io.payflow.android.data.remote.repository

import io.payflow.android.data.remote.api.CatalogApi
import io.payflow.android.data.remote.dto.ServiceDto

open class CatalogRepository {

	open suspend fun getServices(): List<ServiceDto> {
		return CatalogApi.service.getServices()
	}
}
