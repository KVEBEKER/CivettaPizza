package ru.kvebekshaev.civettapizza.domain.entities

data class UserEntity(
    val id:Int,
    val username: String,
    val email: String,
    val phoneNumber: String,
    val roleId: Int
)
