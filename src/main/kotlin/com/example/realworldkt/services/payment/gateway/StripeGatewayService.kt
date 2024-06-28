package com.example.realworldkt.services.payment.gateway

import com.example.realworldkt.dto.payment.PaymentResult
import com.example.realworldkt.dto.request.PaymentCallBackRequest
import com.example.realworldkt.dto.request.PaymentRequest
import com.example.realworldkt.services.api.StripeApiService
import com.example.realworldkt.services.payment.interfaces.IPaymentGatewayService
import org.springframework.stereotype.Service

@Service
class StripeGatewayService(
    private val stripeApiService: StripeApiService
) : IPaymentGatewayService {
    override suspend fun pay(paymentRequest: PaymentRequest): PaymentResult {
        val intent = stripeApiService.createPaymentIntent(
            paymentRequest.amount,
            paymentRequest.currency.name
        )

        return PaymentResult(
            type = "PAYMENT_REDIRECT",
            isSuccess = true,
            message = "generate secret success",
            paymentUrl = null,
            secret= intent.clientSecret
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
