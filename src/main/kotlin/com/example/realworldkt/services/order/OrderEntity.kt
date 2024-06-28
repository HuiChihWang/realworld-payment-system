package com.example.realworldkt.services.order

data class Price(
    val amount: Int,
    val currency: String,
)

data class ProductEntity(
    val productId: Int,
    val productName: String,
    val productDescription: String,
    val productPrice: Price,
)

data class ProductOrderInfo(
    val product: ProductEntity,
    val quantity: Int,
    val totalPrice: Price,
)

data class OrderEntity(
    val userId: Int,
    val orderId: Int,
    val orderProducts: List<ProductOrderInfo>,
    val orderTotalPrice: Price,
    val orderDate: String,
)
