package ru.kvebekshaev.civettapizza.domain.usecases

import ru.kvebekshaev.civettapizza.domain.entities.OrderItemEntity
import ru.kvebekshaev.civettapizza.domain.repositories.OrderItemRepository

class GetItemsUseCase(private val repository: OrderItemRepository) {
    suspend fun execute(): Result<List<OrderItemEntity>>{
        return repository.getItems()
    }
}