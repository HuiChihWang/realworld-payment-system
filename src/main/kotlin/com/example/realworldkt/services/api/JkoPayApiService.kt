package com.example.realworldkt.services.api

import com.example.realworldkt.config.payment.JkoPayApiConfig
import com.example.realworldkt.dto.api.jko.JkoCreateOrderRequest
import com.example.realworldkt.dto.api.jko.JkoCreateOrderResponse
import kotlinx.coroutines.reactor.awaitSingle
import org.apache.commons.codec.digest.HmacAlgorithms
import org.apache.commons.codec.digest.HmacUtils
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.net.URI
import java.util.*

@Service
class JkoPayApiService(
    private val jkoPayApiConfig: JkoPayApiConfig,
    private val httpClient: WebClient.Builder,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    suspend fun createOrder(amount: Number, currency: String, returnUrl: URI): JkoCreateOrderResponse {
        if (currency != "TWD") {
            throw IllegalArgumentException("Currency not supported")
        }

        val fakeOderId = UUID.randomUUID().toString()
        val request = JkoCreateOrderRequest(
            platformOrderId = fakeOderId,
            storeId = jkoPayApiConfig.merchantId,
            currency = "TWD",
            totalPrice = amount,
            finalPrice = amount,
            resultUrl = returnUrl.toString(),
        )

        val requestUri = jkoPayApiConfig.baseUrl.resolve("/platform/entry")
        val payload = request.toJsonString()
        val headers = getHeader(payload)
        logger.info("Create order request: $payload")

        try {
            val res = httpClient.build().post().uri(requestUri)
                .headers { it.setAll(headers) }
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(JkoCreateOrderResponse::class.java)
                .awaitSingle()
            logger.info("Order created response: $res")
            return res
        } catch (e: Exception) {
            logger.error("Failed to create order: ${e.message}")
            throw e
        }
    }

    private fun getHeader(payload: String): Map<String, String> {
        val signature = generateSignature(payload)
        return mapOf(
            HttpHeaders.CONTENT_TYPE to "application/json",
            "API-KEY" to jkoPayApiConfig.apiKey,
            "DIGEST" to signature
        )
    }

    private fun generateSignature(payload: String): String {
        val secretKey = jkoPayApiConfig.apiSecret
        val payloadByte = payload.toByteArray()
        val secretKeyByte = secretKey.toByteArray()
        return HmacUtils(HmacAlgorithms.HMAC_SHA_256, secretKeyByte).hmacHex(payloadByte)
    }
}
