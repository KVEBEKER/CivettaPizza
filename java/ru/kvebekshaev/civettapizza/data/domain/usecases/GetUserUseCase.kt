package ru.kvebekshaev.civettapizza.domain.usecases

import ru.kvebekshaev.civettapizza.domain.entities.UserEntity
import ru.kvebekshaev.civettapizza.domain.repositories.UserRepository

class GetUserUseCase(private val repository: UserRepository) {
    suspend fun execute(userEmail: String, userPassword: String, userRole: Int): Result<UserEntity> {
        return repository.getUserByEmailPassword(userEmail,userPassword, userRole)
    }
}