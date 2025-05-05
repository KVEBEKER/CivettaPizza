package ru.kvebekshaev.civettapizza.domain.usecases

import ru.kvebekshaev.civettapizza.domain.entities.OrderEntity
import ru.kvebekshaev.civettapizza.domain.repositories.OrderRepository

class GetOrdersByUserIdUseCase(val repository: OrderRepository) {
    suspend fun execute(id: Int): Result<List<OrderEntity>>{
        return repository.getOrdersByUserId(id)
    }
}