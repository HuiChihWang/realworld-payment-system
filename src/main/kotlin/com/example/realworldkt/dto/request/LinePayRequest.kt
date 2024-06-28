package com.example.realworldkt.dto.request

data class LinePayConfirmQuery(
    val transactionId: Long,
    val orderId: String,
)
