package ru.kvebekshaev.civettapizza.data.dto

import com.google.gson.annotations.SerializedName

data class OrderDto(
    @SerializedName ("order_id") val orderId: Int,
    @SerializedName ("user_id") val userId: Int,
    @SerializedName ("order_date") val orderDate: String,
    @SerializedName ("total_amount") val totalAmount: Float,
    @SerializedName ("status") val status: String,
)
