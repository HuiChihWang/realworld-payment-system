package com.example.realworldkt.services.payment.gateway

import com.example.realworldkt.dto.payment.PaymentResult
import com.example.realworldkt.dto.request.PaymentCallBackRequest
import com.example.realworldkt.dto.request.PaymentRequest
import com.example.realworldkt.services.api.JkoPayApiService
import com.example.realworldkt.services.payment.interfaces.IPaymentGatewayService
import org.springframework.stereotype.Service
import java.net.URI

@Service
class JkoPayGatewayService(
    private val jkoPayApiService: JkoPayApiService
) : IPaymentGatewayService {
    override suspend fun pay(paymentRequest: PaymentRequest): PaymentResult {

        val response = jkoPayApiService.createOrder(
            amount = paymentRequest.amount,
            currency = paymentRequest.currency.name,
            returnUrl = URI("https://localhost:8080/api/payment/callback/jko"),
        )

        val paymentUrl = response.resultObject?.paymentUrl ?: ""

        return PaymentResult(
            type = "PAYMENT_REDIRECT",
            isSuccess = response.result == "000",
            message = response.message,
            paymentUrl = URI(paymentUrl),
        )
    }

    override suspend fun afterPay(paymentRequest: PaymentCallBackRequest): PaymentResult {
        return PaymentResult(
            type = "PAYMENT_RESULT",
            isSuccess = true,
            message = "Payment success",
            paymentUrl = null,
            secret = null
        )
    }
}
