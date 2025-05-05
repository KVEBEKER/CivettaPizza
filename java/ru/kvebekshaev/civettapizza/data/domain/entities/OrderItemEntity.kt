package ru.kvebekshaev.civettapizza.domain.entities

data class OrderItemEntity(
    val productId: Int,
    val quality: Int,
    val price: Float
)
