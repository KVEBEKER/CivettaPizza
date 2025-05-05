package ru.kvebekshaev.civettapizza.domain.usecases

import ru.kvebekshaev.civettapizza.domain.repositories.OrderItemRepository

class ChangeQualityByIdItemUseCase(private val repository: OrderItemRepository) {
    suspend fun execute(id: Int, changing: Int){
        repository.ChangeQualityById(id, changing)
    }
}