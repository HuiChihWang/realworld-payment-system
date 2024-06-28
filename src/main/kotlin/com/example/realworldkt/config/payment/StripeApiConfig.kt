package com.example.realworldkt.config.payment

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.payment.stripe")
data class StripeApiConfig(
    val baseUrl: String = "",
    val secretKey: String = "",
    val publicKey: String = "",
)
