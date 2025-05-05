package ru.kvebekshaev.civettapizza.data.requestBody

import com.google.gson.annotations.SerializedName

data class PassOrderRequestBody(
    @SerializedName("order_id") val order_id: Int,
    @SerializedName("status") val status: String
)
