package com.example.realworldkt.config

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "app.exchange-api")
data class ExchangeRateApiConfig(
    val exchangeApiBaseUrl: String = ""
)
