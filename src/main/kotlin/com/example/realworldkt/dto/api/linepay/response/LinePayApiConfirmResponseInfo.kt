package com.example.realworldkt.dto.api.linepay.response

data class LinePayApiConfirmResponseInfo(
    val transactionId: Long,
    val orderId: String,
    val regKey: String?,
    val packages: List<PackageResponse>,
    val payInfo: List<PayInfo>,
)

data class PayInfo(
    val method: String,
    val amount: Number
)

data class PackageResponse(
    val id: String,
    val amount: Int,
    val userFee: Number?,
    val userFeeAmount: Int,
)


