package ru.kvebekshaev.civettapizza.domain.usecases

import ru.kvebekshaev.civettapizza.domain.entities.UserEntity
import ru.kvebekshaev.civettapizza.domain.repositories.UserRepository

class RegisterUserUseCase (private val repository: UserRepository) {
    suspend fun execute(user: UserEntity, password: String) : Result<UserEntity> {
        return repository.registerUser(user, password)
    }
}