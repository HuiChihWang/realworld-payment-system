package com.example.realworldkt.dto.api.linepay.request


data class LinePayPaymentRequest(
    val amount: Number,
    val currency: String,
    val orderId: String,
    val packages: List<PackageRequest>,
    val redirectUrls: RedirectUrls,
    val options: Options?,
)

data class RedirectUrls(
    val confirmUrl: String,
    val cancelUrl: String,
)

data class Options(
    val payment: PaymentOptions?,
    val display: DisplayOptions?,
)

data class DisplayOptions(
    val locale: String?,
)

data class PaymentOptions(
    val payType: String?,
    val capture: Boolean = true,
)

data class PackageRequest(
    val id: String,
    val amount: Number,
    val userFee: Number? = null,
    val products: List<Product>,
)

data class Product(
    val id: String? = null,
    val imageUrl: String? = null,
    val name: String,
    val quantity: Int,
    val price: Number,
)



