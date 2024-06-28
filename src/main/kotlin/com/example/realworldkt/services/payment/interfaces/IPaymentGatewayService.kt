package com.example.realworldkt.services.payment.interfaces

import com.example.realworldkt.dto.payment.PaymentResult
import com.example.realworldkt.dto.request.PaymentCallBackRequest
import com.example.realworldkt.dto.request.PaymentRequest

interface IPaymentGatewayService {
    suspend fun pay(paymentRequest: PaymentRequest): PaymentResult
    //TODO: think: How To handle cb request for each payment gateway
    suspend fun afterPay(paymentRequest: PaymentCallBackRequest): PaymentResult
//    suspend fun refund()
}
