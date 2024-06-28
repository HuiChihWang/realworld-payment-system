package com.example.realworldkt.config.payment

import org.springframework.boot.context.properties.ConfigurationProperties
import java.net.URI

@ConfigurationProperties(prefix = "app.payment.jko")
data class JkoPayApiConfig(
    val baseUrl: URI = URI.create(""),
    val merchantId: String = "",
    val apiSecret: String = "",
    val apiKey: String = "",
)
