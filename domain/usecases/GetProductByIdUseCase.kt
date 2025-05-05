package ru.kvebekshaev.civettapizza.domain.usecases

import ru.kvebekshaev.civettapizza.domain.entities.ProductEntity
import ru.kvebekshaev.civettapizza.domain.repositories.ProductRepository

class GetProductByIdUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(id: Int): Result<ProductEntity> {
        return productRepository.getProductById(id)
    }
}