package ru.kvebekshaev.civettapizza.domain.repositories

interface SessionRepository {
    fun saveUserId(id: Int)
    fun getUserId(): Int
    fun saveUsername(username: String)
    fun getUsername(): String?
    fun saveEmail(email: String)
    fun getEmail(): String?
    fun savePhone(phone: String)
    fun getPhone(): String?
    fun saveLogIn(isLoggedIn: Boolean)
    fun getLogIn():Boolean
}