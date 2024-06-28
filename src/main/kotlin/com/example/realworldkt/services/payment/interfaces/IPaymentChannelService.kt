package com.example.realworldkt.services.payment.interfaces

import com.example.realworldkt.dto.payment.PaymentResult
import com.example.realworldkt.dto.request.PaymentRequest

interface IPaymentChannelService {
    suspend fun pay(request: PaymentRequest): PaymentResult
    // TODO: check this return type
//    suspend fun refund(): Boolean
}
