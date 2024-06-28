package com.example.realworldkt.services

import com.example.realworldkt.services.interfaces.IPaymentService
import org.springframework.stereotype.Service

@Service
class PaymentServiceImpl: IPaymentService {
    override suspend fun pay(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun refund(): Boolean {
        TODO("Not yet implemented")
    }
}
