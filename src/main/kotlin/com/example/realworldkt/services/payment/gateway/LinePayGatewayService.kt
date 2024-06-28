package com.example.realworldkt.services.payment.gateway

import com.example.realworldkt.dto.payment.PaymentResult
import com.example.realworldkt.dto.request.PaymentCallBackRequest
import com.example.realworldkt.dto.request.PaymentRequest
import com.example.realworldkt.services.api.LinePayApiService
import com.example.realworldkt.services.payment.interfaces.IPaymentGatewayService
import org.springframework.stereotype.Service
import java.net.URI

@Service
class LinePayGatewayService(
    private val linePayApiService: LinePayApiService
) : IPaymentGatewayService {
    override suspend fun pay(paymentRequest: PaymentRequest): PaymentResult {
        val (amount, currency) = paymentRequest
        val response = linePayApiService.payment(
            amount = amount,
            currency = currency.name,
            // TODO: think how to manage callbacks across different payment gateways
            confirmUri = URI("http://localhost:8080/api/payment/callback/linepay"),
            cancelUri = URI("http://localhost:8080/api/payment/callback/cancel"),
            language = "en",
        )
        return PaymentResult(
            type = "PAYMENT_REDIRECT",
            isSuccess = response.returnCode == "0000",
            message = response.returnMessage,
            paymentUrl = URI(response.info?.paymentUrl?.web ?: ""),
        )
    }

    override suspend fun afterPay(callBackRequest: PaymentCallBackRequest): PaymentResult {
        if (callBackRequest.transactionId == null) {
            return PaymentResult(
                type = "PAYMENT_RESULT",
                isSuccess = false,
                message = "Transaction ID is required",
                paymentUrl = null,
            )
        }

        val response = linePayApiService.confirm(
            amount = 3,
            currency = "TWD",
            transactionId = callBackRequest.transactionId,
        )

        return PaymentResult(
            type = "PAYMENT_RESULT",
            isSuccess = response.returnCode == "0000",
            message = response.returnMessage,
            paymentUrl = null,
        )
    }
}
