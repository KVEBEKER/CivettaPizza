package ru.kvebekshaev.civettapizza.presentation.views

import ru.kvebekshaev.civettapizza.domain.entities.UserEntity

interface RegistrationView {
    fun showUser(user: UserEntity)
    fun showError(e: Throwable)
}