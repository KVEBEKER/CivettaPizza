package ru.kvebekshaev.civettapizza.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "order_item")
data class OrderItemDto(
    @PrimaryKey @ColumnInfo("product_id") @SerializedName("product_id") val productId: Int,
    @ColumnInfo("quality") @SerializedName("quantity") val quantity: Int,
    @ColumnInfo("price") @SerializedName("price") val price: Float
)
