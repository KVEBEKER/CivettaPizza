package ru.kvebekshaev.civettapizza.domain.usecases

import ru.kvebekshaev.civettapizza.domain.repositories.OrderItemRepository

class ClearItemsUseCase(private val repository: OrderItemRepository) {
    fun execute(){
        repository.clearItems()
    }
}