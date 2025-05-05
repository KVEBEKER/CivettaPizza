package ru.kvebekshaev.civettapizza.data.requestBody

import com.google.gson.annotations.SerializedName
import ru.kvebekshaev.civettapizza.data.dto.OrderItemDto

data class SendOrderRequestBody(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("items") val orderItems: List<OrderItemDto>
)
