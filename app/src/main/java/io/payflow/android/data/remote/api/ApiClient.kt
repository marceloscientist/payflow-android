package io.payflow.android.data.remote.api

import io.payflow.android.core.config.AppConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private val okHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(
                AppConfig.CONNECT_TIMEOUT_SECONDS,
                TimeUnit.SECONDS
            )
            .readTimeout(
                AppConfig.READ_TIMEOUT_SECONDS,
                TimeUnit.SECONDS
            )
            .build()

    val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(
                AppConfig.API_BASE_URL
            )
            .client(
                okHttpClient
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
}