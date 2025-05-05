package ru.kvebekshaev.civettapizza.data.responses

import com.google.gson.annotations.SerializedName

data class StringResponse(
    @SerializedName("message") val message: String
)
