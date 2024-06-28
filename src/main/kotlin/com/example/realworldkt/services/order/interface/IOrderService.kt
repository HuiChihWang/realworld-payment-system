package com.example.realworldkt.services.order.`interface`

//TODO: declare IOrderService
interface IOrderService<OrderID, OrderEntity> {
    suspend fun getOrder(orderId: OrderID): OrderEntity
}
