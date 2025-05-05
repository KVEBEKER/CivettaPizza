package ru.kvebekshaev.civettapizza.domain.entities

data class OrderEntity(
    val orderId: Int,
    val orderItems: List<OrderItemEntity>,
    val totalAmount: Float,
    val status: String
)
