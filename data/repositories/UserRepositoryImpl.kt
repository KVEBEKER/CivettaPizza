package ru.kvebekshaev.civettapizza.data.repositories

import ru.kvebekshaev.civettapizza.data.db.api.ApiService
import ru.kvebekshaev.civettapizza.data.dto.UserDto
import ru.kvebekshaev.civettapizza.domain.entities.UserEntity
import ru.kvebekshaev.civettapizza.domain.repositories.UserRepository

class UserRepositoryImpl(private val apiService: ApiService) : UserRepository {
    override suspend fun getUserByEmailPassword(
        email: String,
        password: String,
        roleId: Int
    ): Result<UserEntity> = try {
        val user = apiService.getUser(UserDto(0,email,email,"8",password,roleId))
        if (user.isSuccessful)
        {
            val userBody = user.body()!!
            Result.success(userBody.toDomain())
        }else{
            Result.failure(Throwable(user.message()))
        }
    } catch (e: Exception){
        Result.failure(e)
    }

    override suspend fun registerUser(user: UserEntity, password: String) : Result<UserEntity> {
        return try {
            val responseRegister = apiService.postUser(user.toDatabase(password))
            if (responseRegister.isSuccessful)
            {
                val responseLogin = apiService.getUser(user.toDatabase(password))
                if (responseLogin.isSuccessful)
                {
                    val userBody = responseLogin.body()!!
                    Result.success(userBody.toDomain())
                }else{
                    Result.failure(Throwable(responseLogin.message()))
                }
            } else {
                Result.failure(Throwable(responseRegister.message()))
            }
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }
}


private fun UserDto.toDomain() = UserEntity(
    id = this.user_id,
    username = this.username,
    email = this.email,
    phoneNumber = this.phone_number,
    roleId = role_id
)

private fun UserEntity.toDatabase() = UserDto(
    user_id = this.id,
    username = this.username,
    email = this.email,
    phone_number =  this.phoneNumber,
    password = null,
    role_id = this.roleId
)

private fun UserEntity.toDatabase(password: String) = UserDto(
    user_id = this.id,
    username = this.username,
    email = this.email,
    phone_number =  this.phoneNumber,
    password = password,
    role_id = this.roleId
)