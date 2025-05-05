package ru.kvebekshaev.civettapizza.domain.entities

data class ProductEntity(
    val productId: Int,
    val productName: String,
    val description: String,
    val imageLink: ByteArray?,
    val price: Float,
    val category: ProductCategory,
    val isAvailable: Boolean
)
