package ru.kvebekshaev.civettapizza.domain.usecases

import ru.kvebekshaev.civettapizza.domain.entities.OrderEntity
import ru.kvebekshaev.civettapizza.domain.repositories.OrderRepository

class GetPendingOrdersUseCase(private val repository: OrderRepository) {
    suspend fun execute(): Result<List<OrderEntity>>{
        return repository.getPendingOrders()
    }
}