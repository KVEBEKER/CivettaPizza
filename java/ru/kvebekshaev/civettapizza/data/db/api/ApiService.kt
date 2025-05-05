package ru.kvebekshaev.civettapizza.data.db.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import ru.kvebekshaev.civettapizza.data.dto.OrderDto
import ru.kvebekshaev.civettapizza.data.dto.OrderItemDto
import ru.kvebekshaev.civettapizza.data.dto.ProductDto
import ru.kvebekshaev.civettapizza.data.dto.UserDto
import ru.kvebekshaev.civettapizza.data.requestBody.GetOrderItemsByOrderIdRequestBody
import ru.kvebekshaev.civettapizza.data.requestBody.GetOrdersByIdRequestBody
import ru.kvebekshaev.civettapizza.data.requestBody.PassOrderRequestBody
import ru.kvebekshaev.civettapizza.data.requestBody.SendOrderRequestBody
import ru.kvebekshaev.civettapizza.data.responses.StringResponse

interface ApiService {
    //User
    @GET("/admin/users")
    suspend fun getUsers(): List<UserDto>

    @POST("/loginalt")
    suspend fun getUser(
        @Body user: UserDto
    ): Response<UserDto>

    @POST("/register")
    suspend fun postUser(
        @Body user: UserDto)
    : Response<StringResponse>
    //Products
    @GET("/client/catalog")
    suspend fun getProducts(): List<ProductDto>

    //Order
    @POST("/client/orderalt")
    suspend fun sendOrder(@Body order: SendOrderRequestBody) : Response<StringResponse>

    @POST("/client/ordersgetalt")
    suspend fun getOrdersByUserId(@Body id: GetOrdersByIdRequestBody) : Response<List<OrderDto>>

    @POST("/client/ordersitemsalt")
    suspend fun getOrderItemsByOrderId(@Body id: GetOrderItemsByOrderIdRequestBody) : Response<List<OrderItemDto>>

    @PUT("/admin/passorder")
    suspend fun passOrder(@Body body: PassOrderRequestBody) : Response<StringResponse>

    @GET("/admin/ordersgetadmin")
    suspend fun getPendingOrders(): List<OrderDto>

    @GET("/admin/ordersgetadminpass")
    suspend fun getPassedOrders(): List<OrderDto>
}