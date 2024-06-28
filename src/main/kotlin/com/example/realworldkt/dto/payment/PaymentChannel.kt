package com.example.realworldkt.dto.payment

import java.net.URI

data class PaymentChannelInterface(
    val name: String,
    val logoUrl: URI,
)

data class PaymentChannel(
    val code: String,
    val paymentUrl: String,
    val paymentMethod: String,
    val interfaceSetting: PaymentChannelInterface,
)
