package io.payflow.android.data.remote.api

object CatalogApi {

	val service: ApiService by lazy {
		ApiClient.retrofit.create(ApiService::class.java)
	}
}
