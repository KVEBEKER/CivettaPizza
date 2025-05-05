package ru.kvebekshaev.civettapizza.domain.usecases

import ru.kvebekshaev.civettapizza.domain.repositories.OrderItemRepository

class DeleteItemByIdUseCase(private val repository: OrderItemRepository) {
    suspend fun execute(id: Int){
        repository.deleteItemById(id)
    }
}