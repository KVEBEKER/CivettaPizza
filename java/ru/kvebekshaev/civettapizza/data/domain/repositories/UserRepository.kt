package ru.kvebekshaev.civettapizza.domain.repositories

import ru.kvebekshaev.civettapizza.domain.entities.UserEntity

interface UserRepository {
    suspend fun getUserByEmailPassword(email: String, password: String, roleId: Int): Result<UserEntity>
    suspend fun registerUser(user: UserEntity, password: String) : Result<UserEntity>
}