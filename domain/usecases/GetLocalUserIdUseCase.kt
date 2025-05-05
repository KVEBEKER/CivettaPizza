package ru.kvebekshaev.civettapizza.domain.usecases

import ru.kvebekshaev.civettapizza.domain.repositories.SessionRepository

class GetLocalUserIdUseCase(private val repository: SessionRepository) {
    fun execute(): Int {
        return repository.getUserId()
    }
}