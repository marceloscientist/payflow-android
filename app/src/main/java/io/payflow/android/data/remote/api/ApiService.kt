package io.payflow.android.data.remote.api

import io.payflow.android.data.remote.dto.ServiceDto
import retrofit2.http.GET

interface ApiService {

	@GET("services")
	suspend fun getServices(): List<ServiceDto>
}