package io.payflow.android.data.remote.api

import io.payflow.android.core.config.AppConfig
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private val mockCatalogResponse =
        """
        [
          {"id":"netflix","name":"Netflix","category":"STREAMING","price":44.90},
          {"id":"spotify","name":"Spotify","category":"MUSIC","price":21.90},
          {"id":"disney-plus","name":"Disney+","category":"STREAMING","price":33.90},
          {"id":"prime-video","name":"Prime Video","category":"STREAMING","price":19.90},
          {"id":"xbox-game-pass","name":"Xbox Game Pass","category":"GAMES","price":49.90},
          {"id":"playstation-plus","name":"PlayStation Plus","category":"GAMES","price":34.90},
          {"id":"chatgpt-plus","name":"ChatGPT Plus","category":"AI_PRODUCTIVITY","price":99.90},
          {"id":"canva-pro","name":"Canva Pro","category":"AI_PRODUCTIVITY","price":34.90},
          {"id":"google-one","name":"Google One","category":"CLOUD_STORAGE","price":9.99},
          {"id":"duolingo-super","name":"Duolingo Super","category":"EDUCATION","price":29.90}
        ]
        """.trimIndent()

    private val okHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()

                if (request.url.host == "payflow.mock" && request.url.encodedPath == "/${AppConfig.SERVICES_ENDPOINT}") {
                    Response.Builder()
                        .request(request)
                        .protocol(Protocol.HTTP_1_1)
                        .code(200)
                        .message("OK")
                        .body(
                            mockCatalogResponse.toResponseBody(
                                "application/json".toMediaType()
                            )
                        )
                        .build()
                } else {
                    chain.proceed(request)
                }
            }
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