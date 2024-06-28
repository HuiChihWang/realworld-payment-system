package com.example.realworldkt.services.order

import com.example.realworldkt.services.order.`interface`.IOrderService
import org.springframework.stereotype.Service

@Service
class MockedOrderService: IOrderService<Int, OrderEntity> {
    override suspend fun getOrder(orderId: Int): OrderEntity {
        val productOrderInfo1 = ProductOrderInfo(
            product = ProductEntity(
                productId = 1,
                productName = "mocked product",
                productDescription = "mocked product description",
                productPrice = Price(
                    amount = 100,
                    currency = "USD")
            ),
            quantity = 2,
            totalPrice = Price(
                amount = 200,
                currency = "USD"

            )
        )

        val productOrderInfo2 = ProductOrderInfo(
            product = ProductEntity(
                productId = 2,
                productName = "mocked product 2",
                productDescription = "mocked product description 2",
                productPrice = Price(
                    amount = 300,
                    currency = "USD"
                )
            ),
            quantity = 3,
            totalPrice = Price(
                amount = 900,
                currency = "USD"
            )
        )

        return OrderEntity(
            userId = 1,
            orderId = orderId,
            orderProducts = listOf(productOrderInfo1, productOrderInfo2),
            orderTotalPrice = Price(
                amount = 1100,
                currency = "USD"
            ),
            orderDate = "2024-06-20"
        )
    }


}
