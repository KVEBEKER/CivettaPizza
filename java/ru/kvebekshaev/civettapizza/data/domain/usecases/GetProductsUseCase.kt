package ru.kvebekshaev.civettapizza.domain.usecases

import ru.kvebekshaev.civettapizza.domain.entities.ProductEntity
import ru.kvebekshaev.civettapizza.domain.repositories.ProductRepository

class GetProductsUseCase(private val repository: ProductRepository) {
    suspend fun execute(): Result<List<ProductEntity>> {
        return repository.getProducts()
    }
}