package ru.kvebekshaev.civettapizza.domain.repositories

import ru.kvebekshaev.civettapizza.domain.entities.OrderEntity

interface OrderRepository {
    suspend fun getOrdersByUserId(id: Int): Result<List<OrderEntity>>
    suspend fun getPendingOrders(): Result<List<OrderEntity>>
    suspend fun getPassedOrders(): Result<List<OrderEntity>>
    suspend fun passOrder(id: Int): Result<String>
}