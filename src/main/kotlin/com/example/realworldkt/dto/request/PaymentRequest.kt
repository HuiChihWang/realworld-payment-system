package com.example.realworldkt.dto.request

import com.example.realworldkt.enums.Currency
import jakarta.validation.constraints.Positive

data class PaymentRequest(
    @field:Positive
    val amount: Number,
    val currency: Currency,
)

data class PaymentCallBackRequest(
    val transactionId: Long?,
    val orderId: String?,
    val amount: Number,
    val currency: Currency,
)
