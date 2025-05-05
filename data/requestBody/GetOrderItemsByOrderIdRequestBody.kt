package ru.kvebekshaev.civettapizza.data.requestBody

import com.google.gson.annotations.SerializedName

data class GetOrderItemsByOrderIdRequestBody(
    @SerializedName("order_id") val orderId: Int
)
