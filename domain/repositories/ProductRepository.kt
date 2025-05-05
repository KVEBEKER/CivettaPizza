package ru.kvebekshaev.civettapizza.domain.repositories

import ru.kvebekshaev.civettapizza.domain.entities.ProductEntity

interface ProductRepository {
    suspend fun getProducts(): Result<List<ProductEntity>>
    suspend fun getProductById(id: Int): Result<ProductEntity>
}   