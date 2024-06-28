package com.example.realworldkt.services.currency

import com.example.realworldkt.config.CacheConfig
import com.example.realworldkt.config.ExchangeRateApiConfig
import com.example.realworldkt.enums.Currency
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder


@Service
class ExchangeRateApiService(
    private val exchangeRateApiConfig: ExchangeRateApiConfig,
    private val httpClient: WebClient.Builder,
) {
    @Cacheable(value = [CacheConfig.EXCHANGE_RATES_API_CACHE], key = "#from.name")
    suspend fun fetchExchangeRates(from: Currency): Map<String, Double>? {
        val exchangeApiUrl = UriComponentsBuilder
            .fromHttpUrl(exchangeRateApiConfig.exchangeApiBaseUrl)
            .pathSegment(from.name)
            .build()
            .toString()

        val typeRef = object : ParameterizedTypeReference<Map<String, *>>() {}
        val responseBody = httpClient.build().get().uri(exchangeApiUrl)
            .retrieve().bodyToMono(typeRef)
            .awaitSingleOrNull()
        val conversionRates = responseBody?.get("conversion_rates") as Map<String, Double>?

        return conversionRates ?: emptyMap()
    }
}
