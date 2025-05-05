package ru.kvebekshaev.civettapizza.domain.usecases

import ru.kvebekshaev.civettapizza.domain.entities.OrderItemEntity
import ru.kvebekshaev.civettapizza.domain.repositories.OrderItemRepository

class GetItemByIdUseCase(private val repository: OrderItemRepository) {
    suspend fun execute(id: Int): Result<OrderItemEntity>{
        return repository.getItemById(id)
    }
}