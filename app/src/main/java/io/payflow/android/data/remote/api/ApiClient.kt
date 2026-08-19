package io.payflow.android.data.remote.api

import io.payflow.android.BuildConfig
import io.payflow.android.core.config.AppConfig
import io.payflow.android.core.utils.Logger
import okhttp3.OkHttpClient
import okhttp3.Dns
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.UnknownHostException
import java.net.Proxy
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import java.io.IOException
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object ApiClient {

    private const val TAG = "ApiClient"
    // Workaround para ambiente corporativo: fixa IPs do host quando o Android
    // do emulador nao consegue resolver o DNS normalmente.
    // Remocao: apague DEFAULT_DNS_OVERRIDE_HOST, DEFAULT_DNS_OVERRIDE_ADDRESSES
    // e a chamada dns(StaticHostDns(...)) abaixo quando o ambiente estiver normalizado.
    private const val DEFAULT_DNS_OVERRIDE_HOST = "lucascall.github.io"
    private val DEFAULT_DNS_OVERRIDE_ADDRESSES = listOf(
        "185.199.108.153",
        "185.199.109.153",
        "185.199.110.153",
        "185.199.111.153"
    )

    private val effectiveDnsOverrideHost =
        BuildConfig.DNS_OVERRIDE_HOST.ifBlank { DEFAULT_DNS_OVERRIDE_HOST }

    private val effectiveDnsOverrideAddresses =
        BuildConfig.DNS_OVERRIDE_ADDRESSES.ifBlank {
            DEFAULT_DNS_OVERRIDE_ADDRESSES.joinToString(separator = ",")
        }

    // Workaround de certificado apenas para teste em ambiente corporativo/debug.
    // Isso desabilita a validacao de cadeia SSL e hostname, portanto nao deve
    // permanecer habilitado fora desse contexto.
    // Remocao: apagar unsafeTrustManager, unsafeSslContext, UnsafeHostnameVerifier
    // e o bloco if (BuildConfig.DEBUG && BuildConfig.ENABLE_UNSAFE_SSL) abaixo.
    private val unsafeTrustManager = object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) = Unit

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) = Unit

        override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
    }

    private val unsafeSslContext: SSLContext by lazy {
        SSLContext.getInstance("TLS").apply {
            init(null, arrayOf<TrustManager>(unsafeTrustManager), SecureRandom())
        }
    }

    private val okHttpClient =
        OkHttpClient.Builder()
            .apply {
                Logger.debug(
                    TAG,
                    "Inicializando cliente HTTP baseUrl=${AppConfig.API_BASE_URL}, proxyHost=${BuildConfig.HTTP_PROXY_HOST}, proxyPort=${BuildConfig.HTTP_PROXY_PORT}, unsafeSsl=${BuildConfig.ENABLE_UNSAFE_SSL}, dnsOverrideHost=$effectiveDnsOverrideHost, dnsOverrideAddresses=$effectiveDnsOverrideAddresses"
                )

                if (BuildConfig.HTTP_PROXY_HOST.isNotBlank() && BuildConfig.HTTP_PROXY_PORT > 0) {
                    proxy(
                        Proxy(
                            Proxy.Type.HTTP,
                            InetSocketAddress(
                                BuildConfig.HTTP_PROXY_HOST,
                                BuildConfig.HTTP_PROXY_PORT
                            )
                        )
                    )
                }

                if (BuildConfig.DEBUG && BuildConfig.ENABLE_UNSAFE_SSL) {
                    sslSocketFactory(unsafeSslContext.socketFactory, unsafeTrustManager)
                    hostnameVerifier(UnsafeHostnameVerifier)
                }

                // Mantem a URL real da API, mas contorna falha de DNS observada
                // no emulador em ambiente corporativo.
                dns(
                    StaticHostDns(
                        host = effectiveDnsOverrideHost,
                        rawAddresses = effectiveDnsOverrideAddresses
                    )
                )
            }
            .addInterceptor { chain ->
                val request = chain.request()

                Logger.debug(
                    TAG,
                    "Request ${request.method} ${request.url}"
                )

                try {
                    val response = chain.proceed(request)

                    Logger.debug(
                        TAG,
                        "Response ${response.code} ${response.message} ${request.url}"
                    )

                    response
                } catch (exception: IOException) {
                    Logger.error(
                        TAG,
                        "Falha de rede em ${request.url}: ${exception.message}",
                        exception
                    )
                    throw exception
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

    private object UnsafeHostnameVerifier : HostnameVerifier {
        override fun verify(hostname: String?, session: SSLSession?): Boolean = true
    }

    private class StaticHostDns(
        private val host: String,
        rawAddresses: String
    ) : Dns {

        private val addresses = rawAddresses
            .split(',')
            .map(String::trim)
            .filter(String::isNotBlank)

        override fun lookup(hostname: String): List<InetAddress> {
            if (!hostname.equals(host, ignoreCase = true)) {
                return Dns.SYSTEM.lookup(hostname)
            }

            Logger.debug(TAG, "Aplicando override de DNS para $hostname com IPs: ${addresses.joinToString()}")

            return try {
                addresses.map { address -> InetAddress.getByName(address) }
            } catch (exception: UnknownHostException) {
                Logger.error(TAG, "Falha ao resolver override de DNS para $hostname", exception)
                throw exception
            }
        }
    }

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