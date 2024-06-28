package com.example.realworldkt.services.payment.channels

import com.example.realworldkt.dto.payment.PaymentResult
import com.example.realworldkt.dto.request.PaymentRequest
import com.example.realworldkt.factories.payment.PaymentGatewayServiceFactory
import com.example.realworldkt.services.payment.interfaces.IPaymentChannelService
import org.springframework.stereotype.Service

@Service
class RandomChannelService(
    private val gatewayServiceFactory: PaymentGatewayServiceFactory,
) : IPaymentChannelService {
    override suspend fun pay(request: PaymentRequest): PaymentResult {
        val random = (0..1).random()
        val gatewayName = if (random == 0) "LINE_PAY" else "STRIPE"
        val gatewayService = gatewayServiceFactory.getPaymentGatewayService(gatewayName)
        val result = gatewayService.pay(request)
        return result
    }
}
