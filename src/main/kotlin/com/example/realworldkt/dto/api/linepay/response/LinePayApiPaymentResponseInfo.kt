package com.example.realworldkt.dto.api.linepay.response


data class LinePayApiPaymentResponseInfo(
    val transactionId: Long,
    val paymentUrl: PaymentUrls,
    val paymentAccessToken: String,
)


data class PaymentUrls(
    val web: String,
    val app: String
)
