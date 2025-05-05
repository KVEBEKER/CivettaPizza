package ru.kvebekshaev.civettapizza.domain.usecases

import ru.kvebekshaev.civettapizza.domain.entities.OrderItemEntity
import ru.kvebekshaev.civettapizza.domain.repositories.OrderItemRepository

class SendOrderUseCase(private val repository: OrderItemRepository) {
    suspend fun execute(id: Int, items: List<OrderItemEntity>): Result<String>{
        return repository.sendOrder(id, items)
    }
}