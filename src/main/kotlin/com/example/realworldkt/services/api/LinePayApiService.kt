package com.example.realworldkt.services.api

import com.example.realworldkt.config.payment.LinePayApiConfig
import com.example.realworldkt.dto.api.linepay.request.*
import com.example.realworldkt.dto.api.linepay.response.LinePayApiConfirmResponseInfo
import com.example.realworldkt.dto.api.linepay.response.LinePayApiPaymentResponseInfo
import com.example.realworldkt.dto.api.linepay.response.LinePayApiResponse
import com.google.gson.Gson
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.apache.commons.codec.digest.HmacAlgorithms
import org.apache.commons.codec.digest.HmacUtils
import org.slf4j.LoggerFactory
import org.springframework.boot.json.GsonJsonParser
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.util.*

//TODO: handle Error
//TODO: refund
@Service
class LinePayApiService(
    private val linePayApiConfig: LinePayApiConfig,
    private val httpClient: WebClient.Builder,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    suspend fun payment(
        amount: Number,
        currency: String,
        confirmUri: URI,
        cancelUri: URI,
        language: String,
    ): LinePayApiResponse<LinePayApiPaymentResponseInfo> {
        val requestUri = "/v3/payments/request"
        val paymentUrl = getPaymentUrl(requestUri)

        val fakeOrderId = UUID.randomUUID().toString()
        val request = LinePayPaymentRequest(
            amount = amount,
            currency = currency,

            //TODO: set real product info
            orderId = fakeOrderId,
            packages = listOf(
                PackageRequest(
                    id = "package-123",
                    amount = amount,
                    products = listOf(
                        Product(
                            name = "product-123",
                            quantity = 1,
                            price = amount
                        )
                    )
                )
            ),
            redirectUrls = RedirectUrls(
                confirmUrl = confirmUri.toString(),
                cancelUrl = cancelUri.toString()
            ),
            options = Options(
                payment = PaymentOptions(
                    payType = "PREAPPROVED",
                    capture = true,
                ),
                display = DisplayOptions(
                    locale = language
                )
            )
        )

        val requestString = Gson().toJson(request)
        val headers = getHeaders(requestString, requestUri)
        val res = httpClient.build().post().uri(paymentUrl)
            .headers { it.setAll(headers) }
            .bodyValue(requestString)
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<LinePayApiResponse<LinePayApiPaymentResponseInfo>>() {})
            .awaitSingleOrNull()

        if (res == null) {
            logger.error("payment response is null")
            throw Exception("payment response is null")
        } else {
            logger.info("payment response: $res")
        }

        return res
    }

    suspend fun confirm(amount: Number, currency: String, transactionId: Long): LinePayApiResponse<LinePayApiConfirmResponseInfo> {
        val requestUri = "/v3/payments/$transactionId/confirm"

        val request = LinePayConfirmRequest(
            amount = amount,
            currency = currency
        )

        val requestString = Gson().toJson(request)
        val headers = getHeaders(requestString, requestUri)
        val confirmUrl = getPaymentUrl(requestUri)

        val res = httpClient.build().post().uri(confirmUrl)
            .bodyValue(requestString)
            .headers { it.setAll(headers) }
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<LinePayApiResponse<LinePayApiConfirmResponseInfo>>() {})
            .awaitSingleOrNull()

        if (res == null) {
            logger.error("confirm response is null")
            throw Exception("confirm response is null")
        } else {
            logger.info("confirm response: $res")
        }

        return res
    }

    suspend fun refund(transactionId: String, amount: Number): Map<String, Any> {
        val requestUri = "/v3/payments/$transactionId/refund"
        val request = LinePayRefundRequest(
            refundAmount = amount,
        )
        val requestString = Gson().toJson(request)
        val headers = getHeaders(requestString, requestUri)

        val refundUrl = getPaymentUrl(requestUri)

        val res = httpClient.build().post().uri(refundUrl)
            .headers { it.setAll(headers) }
            .bodyValue(requestString)
            .retrieve()
            .bodyToMono(String::class.java)
            .awaitSingleOrNull()

        return GsonJsonParser().parseMap(res)
    }

    private fun getPaymentUrl(endPoint: String): URI {
        return UriComponentsBuilder
            .fromHttpUrl(linePayApiConfig.baseUrl)
            .path(endPoint)
            .build()
            .toUri()
    }

    private fun getHeaders(request: String, requestUri: String): Map<String, String> {
        val nonce = UUID.randomUUID().toString()
        return mapOf(
            HttpHeaders.CONTENT_TYPE to "application/json",
            "X-LINE-ChannelId" to linePayApiConfig.channelId,
            "X-LINE-Authorization-Nonce" to nonce,
            "X-LINE-Authorization" to generateSignature(request, requestUri, nonce)
        )
    }

    private fun generateSignature(payload: String, requestUri: String, nonce: String): String {
        val data = linePayApiConfig.channelSecret + requestUri + payload + nonce;
        val hmacUtil = HmacUtils(HmacAlgorithms.HMAC_SHA_256, linePayApiConfig.channelSecret)
        val hmacSignature = hmacUtil.hmac(data);
        return Base64.getEncoder().encodeToString(hmacSignature);
    }
}
