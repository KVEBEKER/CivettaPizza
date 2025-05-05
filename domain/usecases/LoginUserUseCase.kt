package ru.kvebekshaev.civettapizza.domain.usecases

import ru.kvebekshaev.civettapizza.domain.entities.UserEntity
import ru.kvebekshaev.civettapizza.domain.repositories.SessionRepository

class LoginUserUseCase(private val sessionRepository: SessionRepository) {
    suspend fun execute(user: UserEntity){
        sessionRepository.saveUserId(user.id)
        sessionRepository.saveUsername(user.username)
        sessionRepository.saveEmail(user.email)
        sessionRepository.savePhone(user.phoneNumber)
        sessionRepository.saveLogIn(true)
    }
}