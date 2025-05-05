package ru.kvebekshaev.civettapizza.data.repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.kvebekshaev.civettapizza.data.db.api.ApiService
import ru.kvebekshaev.civettapizza.data.db.local.OrderItemDao
import ru.kvebekshaev.civettapizza.data.dto.OrderItemDto
import ru.kvebekshaev.civettapizza.data.requestBody.SendOrderRequestBody
import ru.kvebekshaev.civettapizza.domain.entities.OrderItemEntity
import ru.kvebekshaev.civettapizza.domain.entities.ProductEntity
import ru.kvebekshaev.civettapizza.domain.repositories.OrderItemRepository

class OrderItemRepositoryImpl(private val apiService: ApiService, private val orderItemDao: OrderItemDao): OrderItemRepository {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    override fun addItem(item: ProductEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            orderItemDao.insertOrderItem(item.toItem())
        }
    }

    override fun replaceItem(item: OrderItemEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            orderItemDao.insertOrderItem(item.toDatabase())
        }
    }

    override fun ChangeQualityById(id: Int, changing: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            orderItemDao.ChangeQualityById(id, changing)
        }
    }

    override suspend fun getItems(): Result<List<OrderItemEntity>> {
        return try {
            val items = orderItemDao.getAllOrderItem().first()
            Result.success(items.map { it.toDomain() })
        }
        catch(e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getItemById(id: Int): Result<OrderItemEntity> {
        return try {
            val itemEntity = orderItemDao.getOrderItemById(id).first().toDomain()
            Result.success(itemEntity)
        }
        catch(e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getItemsByOrderId(id: Int): Result<OrderItemEntity> {
        TODO("Not yet implemented")
    }

    override fun deleteItemById(id: Int) {
        coroutineScope.launch (Dispatchers.IO){
            orderItemDao.deleteOrderItem(id)
        }
    }

    override fun clearItems() {
        coroutineScope.launch (Dispatchers.IO){
            orderItemDao.clearOrderItem()
        }
    }

    override suspend fun sendOrder(id: Int, items: List<OrderItemEntity>): Result<String> {
        return try {
            val call = apiService.sendOrder(SendOrderRequestBody(id,items.map {it.toDatabase()}))
            if (call.isSuccessful){
                val message = call.body()!!
                Result.success(message.message)
            }
            else Result.failure(Throwable(call.errorBody()?.string() ?: "Unknown error"))
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }
}

private fun ProductEntity.toItem() = OrderItemDto(
    productId = this.productId,
    quantity = 1,
    price = this.price
)

private fun OrderItemDto.toDomain() = OrderItemEntity(
    productId = this.productId,
    quality = this.quantity,
    price = this.price
)
private fun OrderItemEntity.toDatabase() = OrderItemDto(
    productId = this.productId,
    quantity = this.quality,
    price = this.price
)