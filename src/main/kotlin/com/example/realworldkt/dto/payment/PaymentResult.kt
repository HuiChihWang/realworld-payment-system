package com.example.realworldkt.dto.payment

import java.net.URI

//TODO: think about how to define a common payment result
data class PaymentResult(
    val type: String,
    val isSuccess: Boolean,
    val message: String?,
    val paymentUrl: URI? = null,
    val paymentId: String? = null,
    val secret: String? = null,
)
