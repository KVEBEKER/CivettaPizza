package ru.kvebekshaev.civettapizza.domain.repositories

import ru.kvebekshaev.civettapizza.domain.entities.OrderItemEntity
import ru.kvebekshaev.civettapizza.domain.entities.ProductEntity

interface OrderItemRepository {
    fun addItem(item: ProductEntity)
    fun replaceItem(item: OrderItemEntity)
    fun ChangeQualityById(id: Int, changing: Int)
    suspend fun getItems(): Result<List<OrderItemEntity>>
    suspend fun getItemById(id: Int): Result<OrderItemEntity>
    suspend fun getItemsByOrderId(id: Int): Result<OrderItemEntity>
    fun deleteItemById(id: Int)
    fun clearItems()
    suspend fun sendOrder(id: Int, items: List<OrderItemEntity>): Result<String>
}