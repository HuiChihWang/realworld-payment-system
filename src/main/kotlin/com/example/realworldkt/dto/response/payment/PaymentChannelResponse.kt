package com.example.realworldkt.dto.response.payment

data class PaymentChannelResponse(
    val code: String,
    val paymentUrl: String,
    val paymentMethod: String,
)
