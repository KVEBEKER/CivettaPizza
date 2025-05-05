package ru.kvebekshaev.civettapizza.domain.usecases

import ru.kvebekshaev.civettapizza.domain.repositories.OrderRepository

class PassOrderUseCase(private val repository: OrderRepository) {
    suspend fun execute(order_id: Int): Result<String>{
        return repository.passOrder(order_id)
    }
}