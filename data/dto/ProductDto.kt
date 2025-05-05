package ru.kvebekshaev.civettapizza.data.dto

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ProductDto(
    @PrimaryKey @SerializedName("product_id") val productId: Int,
    @SerializedName("product_name") val productName: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val imageLink: ByteArray?,
    @SerializedName("price") val price: Float,
    @SerializedName("category_id") val category: Int,
    @SerializedName("is_available") val isAvailable: Boolean
)
