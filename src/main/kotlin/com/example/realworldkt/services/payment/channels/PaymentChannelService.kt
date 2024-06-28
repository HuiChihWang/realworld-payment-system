package com.example.realworldkt.services.payment.channels


import com.example.realworldkt.dto.payment.PaymentResult
import com.example.realworldkt.dto.request.PaymentRequest
import com.example.realworldkt.services.payment.interfaces.IPaymentChannelService
import com.example.realworldkt.services.payment.interfaces.IPaymentGatewayService

class PaymentChannelService(
    private val gatewayService: IPaymentGatewayService,
) : IPaymentChannelService {
    // Test: create one channel connect to line pay
    // Test: create one channel connect to stripe
    // Test: create one channel random choose one of them
    override suspend fun pay(request: PaymentRequest): PaymentResult {
        // pay


        // create payment record

        // setup result: redirect url, payment id, etc
        return gatewayService.pay(request)
    }
}
