package ru.kvebekshaev.civettapizza.data.requestBody

import com.google.gson.annotations.SerializedName

data class GetOrdersByIdRequestBody(
    @SerializedName("user_id") val id: Int
)
