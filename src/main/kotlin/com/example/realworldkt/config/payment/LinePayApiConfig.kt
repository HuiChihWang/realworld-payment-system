package com.example.realworldkt.config.payment

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.payment.linepay")
data class LinePayApiConfig(
    val baseUrl: String = "",
    val channelId: String = "",
    val channelSecret: String = "",
    val confirmUrl: String = "",
)
