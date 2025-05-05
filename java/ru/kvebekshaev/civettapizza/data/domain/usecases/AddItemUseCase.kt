package ru.kvebekshaev.civettapizza.domain.usecases

import ru.kvebekshaev.civettapizza.domain.entities.ProductEntity
import ru.kvebekshaev.civettapizza.domain.repositories.OrderItemRepository

class AddItemUseCase (private val repository: OrderItemRepository){
    suspend fun execute(item: ProductEntity){
        repository.addItem(item)
    }
}