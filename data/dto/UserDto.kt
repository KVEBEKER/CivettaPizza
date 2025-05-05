package ru.kvebekshaev.civettapizza.data.dto

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class UserDto(
    @PrimaryKey @SerializedName("user_id") val user_id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone_number") val phone_number: String,
    @SerializedName("password") val password: String?,
    @SerializedName("role_id") val role_id: Int
)
