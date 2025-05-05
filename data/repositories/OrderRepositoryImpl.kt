package ru.kvebekshaev.civettapizza.data.repositories

import ru.kvebekshaev.civettapizza.data.db.api.ApiService
import ru.kvebekshaev.civettapizza.data.dto.OrderItemDto
import ru.kvebekshaev.civettapizza.data.requestBody.GetOrderItemsByOrderIdRequestBody
import ru.kvebekshaev.civettapizza.data.requestBody.GetOrdersByIdRequestBody
import ru.kvebekshaev.civettapizza.data.requestBody.PassOrderRequestBody
import ru.kvebekshaev.civettapizza.domain.entities.OrderEntity
import ru.kvebekshaev.civettapizza.domain.entities.OrderItemEntity
import ru.kvebekshaev.civettapizza.domain.repositories.OrderRepository

class OrderRepositoryImpl(private val apiService: ApiService): OrderRepository{
    override suspend fun getOrdersByUserId(id: Int): Result<List<OrderEntity>> {
        return try {
            val ordersResponse = apiService.getOrdersByUserId(GetOrdersByIdRequestBody(id))
            if(ordersResponse.isSuccessful){
                val orders = ordersResponse.body()!!
                var ordersEntity: List<OrderEntity> = emptyList()
                for(order in orders){
                    val orderItemsResponse = apiService.getOrderItemsByOrderId(
                        GetOrderItemsByOrderIdRequestBody(order.orderId)
                    )
                    var orderItems: List<OrderItemDto> = emptyList()
                    if (orderItemsResponse.isSuccessful){
                        orderItems = orderItemsResponse.body()!!
                    }
                    ordersEntity = ordersEntity.plus(
                        OrderEntity(
                            order.orderId,
                            orderItems.map { it.toDomain() },
                            order.totalAmount,
                            order.status
                        )
                    )
                }
                Result.success(ordersEntity)
            } else{
                Result.failure(Throwable(ordersResponse.errorBody().toString()))
            }
        }
        catch(e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getPendingOrders(): Result<List<OrderEntity>> {
        return try {
            val orders = apiService.getPendingOrders()
                var ordersEntity: List<OrderEntity> = emptyList()
                for(order in orders){
                    val orderItemsResponse = apiService.getOrderItemsByOrderId(
                        GetOrderItemsByOrderIdRequestBody(order.orderId)
                    )
                    var orderItems: List<OrderItemDto> = emptyList()
                    if (orderItemsResponse.isSuccessful){
                        orderItems = orderItemsResponse.body()!!
                    }
                    ordersEntity = ordersEntity.plus(
                        OrderEntity(
                            order.orderId,
                            orderItems.map { it.toDomain() },
                            order.totalAmount,
                            order.status
                        )
                    )
                }
                Result.success(ordersEntity)
        }
        catch(e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getPassedOrders(): Result<List<OrderEntity>> {
        return try {
            val orders = apiService.getPassedOrders()
            var ordersEntity: List<OrderEntity> = emptyList()
            for(order in orders){
                val orderItemsResponse = apiService.getOrderItemsByOrderId(
                    GetOrderItemsByOrderIdRequestBody(order.orderId)
                )
                var orderItems: List<OrderItemDto> = emptyList()
                if (orderItemsResponse.isSuccessful){
                    orderItems = orderItemsResponse.body()!!
                }
                ordersEntity = ordersEntity.plus(
                    OrderEntity(
                        order.orderId,
                        orderItems.map { it.toDomain() },
                        order.totalAmount,
                        order.status
                    )
                )
            }
            Result.success(ordersEntity)
        }
        catch(e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun passOrder(id: Int): Result<String> {
        return try {
            val call = apiService.passOrder(PassOrderRequestBody(id,"Passed"))
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
private fun OrderItemDto.toDomain() = OrderItemEntity(
    productId = this.productId,
    quality = this.quantity,
    price = this.price
)