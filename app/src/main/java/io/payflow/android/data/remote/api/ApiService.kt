package io.payflow.android.data.remote.api

import io.payflow.android.core.config.AppConfig
import io.payflow.android.data.remote.dto.ServiceDto
import retrofit2.http.GET

interface ApiService {

	@GET(AppConfig.SERVICES_JSON_ENDPOINT)
	suspend fun getServices(): List<ServiceDto>
}